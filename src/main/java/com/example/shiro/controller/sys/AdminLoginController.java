package com.example.shiro.controller.sys;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.example.shiro.common.annotation.ValidateFiled;
import com.example.shiro.common.annotation.ValidateGroup;
import com.example.shiro.common.constants.Administrator;
import com.example.shiro.common.constants.RedisConstants;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.common.enums.EnableStatus;
import com.example.shiro.common.enums.RetCodeEnum;
import com.example.shiro.controller.BaseController;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.exception.LoginException;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.builder.LoginAdminBuilder;
import com.example.shiro.model.builder.SysMenuBuilder;
import com.example.shiro.model.dto.ChangePwdReqDTO;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.model.dto.LoginReqDTO;
import com.example.shiro.model.dto.SysMenuResDTO;
import com.example.shiro.model.sys.SysAdmin;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.service.sys.ISysAdminService;
import com.example.shiro.shiro.AuthorityToken;
import com.example.shiro.shiro.util.ShiroUtil;
import com.example.shiro.utils.ParamsCheckUtils;
import com.example.shiro.utils.TokenGenerator;
import com.google.code.kaptcha.Producer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 管理员登录
 *
 * @Author: yaokui
 * @Date: 2019/6/6
 */
@Slf4j
@RestController
@RequestMapping("/ms/admin")
public class AdminLoginController extends BaseController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired(required = false)
    private Producer producer;

    @Autowired
    private ISysAdminService sysAdminService;

    /**
     * 获取验证码
     *
     * @param response response
     * @param uuid     uuid
     * @throws IOException 异常
     */
    @GetMapping("/login/captcha")
    public void captcha(HttpServletResponse response, @RequestParam("uuid") String uuid) throws IOException {

        // 设置response返回类型
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        if (StringUtils.isEmpty(uuid)) {
            throw new BusinessException(BizExceptionEnum.UUID_BLANK);
        }

        String code = producer.createText();
        redisTemplate.opsForValue().set(RedisConstants.KEY_PREFIX_LOGIN_CAPTCHA + uuid, code, RedisConstants.EXPIRE_10_MINUTES, TimeUnit.SECONDS);

        BufferedImage image = producer.createImage(code);

        // 输出验证码图片
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 用户登陆
     *
     * @param loginReqDTO 登陆信息表单
     * @return 结果
     */
    @PostMapping("/login")
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, filedName = "account", notNull = true,message = "用户名不能为空"),
            @ValidateFiled(index = 0, filedName = "password", notNull = true,message = "密码不能为空")
    })
    public Result login(@RequestBody LoginReqDTO loginReqDTO) {

        //校验参数
        Result result = new Result();
        if (ParamsCheckUtils.isNull(loginReqDTO.getAccount(), loginReqDTO.getPassword())) {
            result.setCode(BizExceptionEnum.REQUEST_INVALIDATE.getFriendlyCode());
            result.setMessage(BizExceptionEnum.REQUEST_INVALIDATE.getFriendlyMsg());
            return result;
        }

        // 验证码校验
        //checkCaptcha(loginReqDTO.getUuid(), loginReqDTO.getCaptcha());

        // 登陆
        result = sysAdminService.loginByAccount(loginReqDTO.getAccount(), loginReqDTO.getPassword());
        if (RetCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            SysAdmin sysAdmin = (SysAdmin) result.getData();
            // 创建token
            String token = sysAdmin.getId() + "_" + TokenGenerator.generateValue();
            redisTemplate.opsForValue().set(RedisConstants.KEY_PREFIX_LOGIN_ACCOUNT + token, loginReqDTO.getAccount(), RedisConstants.EXPIRE_30_MINUTES, TimeUnit.SECONDS);

            // 创建返回对象
            LoginAdminResDTO loginAdminResDTO = LoginAdminBuilder.createLoginUser(sysAdmin);
            loginAdminResDTO.setToken(token);
            log.info("用户登录成功：account:{}, token:{}", loginReqDTO.getAccount(), token);

            //会话计时开始
            ShiroUtil.getSubject().login(new AuthorityToken(token, loginAdminResDTO));

            return Result.success(loginAdminResDTO);
        }
        return result;
    }

    /**
     * 用户登陆
     *
     * @param token 登陆信息表单
     * @return 结果
     */
    @GetMapping("/login/menus")
    public Result loginMenus(@RequestParam(name = "token") String token) {
        //校验参数
        Result result = new Result();
        if (ParamsCheckUtils.isNull(token)) {
            result.setCode(BizExceptionEnum.REQUEST_INVALIDATE.getFriendlyCode());
            result.setMessage(BizExceptionEnum.REQUEST_INVALIDATE.getFriendlyMsg());
            return result;
        }

        String loginAccount = redisTemplate.opsForValue().get(RedisConstants.KEY_PREFIX_LOGIN_ACCOUNT + token);
        if (StringUtils.isEmpty(loginAccount)) {
            result.setCode(BizExceptionEnum.SESSION_TIMEOUT.getFriendlyCode());
            result.setMessage(BizExceptionEnum.SESSION_TIMEOUT.getFriendlyMsg());
            return result;
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        // 获取管理员登录菜单
        List<SysMenu> menuList = sysAdminService.getLoginMenusByAccount(loginAccount);

        Set<SysMenuResDTO> menuSet = new HashSet<>();
        for (SysMenu sysMenu : menuList) {
            if (!Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && !Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && (sysMenu.getId() == 10 || sysMenu.getParentId() == 10)) {
                continue;
            }
            if (Objects.equals(sysMenu.getStatus(), EnableStatus.ENABLED.getCode())) {
                menuSet.add(SysMenuBuilder.modelToDto(sysMenu));
            }

        }

        List<SysMenuResDTO> resourceList = Lists.newArrayList();
        resourceList.addAll(menuSet.stream().sorted(Comparator.comparing(SysMenuResDTO::getMenuId)).collect(Collectors.toList()));

        resourceList = SysMenuBuilder.buildTree(resourceList);

        return Result.success(resourceList);
    }

    /**
     * 用户修改密码
     *
     * @param accountId
     * @param changePwdReqDTO 登陆信息表单
     * @return 结果
     */
    @PostMapping("/changePwd/{accountId}")
    public Result changePwd(@PathVariable Long accountId, @RequestBody ChangePwdReqDTO changePwdReqDTO) {
        //校验参数
        if (accountId == null || ParamsCheckUtils.isNull(changePwdReqDTO.getOldPassword(),
                changePwdReqDTO.getNewPassword(), changePwdReqDTO.getReNewPassword())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        // 验证原密码
        boolean checkOldPwd = sysAdminService.checkOldPwd(accountId, changePwdReqDTO.getOldPassword());
        if (!checkOldPwd) {
            throw new BusinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }

        if (!changePwdReqDTO.getNewPassword().equals(changePwdReqDTO.getReNewPassword())) {
            throw new BusinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }

        if (changePwdReqDTO.getNewPassword().equals(changePwdReqDTO.getOldPassword())) {
            throw new BusinessException(BizExceptionEnum.NEW_PSW_EQ_OLD_PSW);
        }

        // 修改密码
        sysAdminService.updatePassword(accountId, changePwdReqDTO.getNewPassword());
        log.info("修改密码成功");
        return Result.success();
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public Result logOut(@RequestParam(name = "token") String token) {
        // 删除redis缓存
        redisTemplate.delete(RedisConstants.KEY_PREFIX_LOGIN_ACCOUNT + token);

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        ShiroUtil.getSubject().logout();
        return Result.success();
    }

    /**
     * 校验图片验证码
     *
     * @param uuid
     * @param captcha
     */
    private void checkCaptcha(String uuid, String captcha) {
        String redisCaptcha = redisTemplate.opsForValue().get(RedisConstants.KEY_PREFIX_LOGIN_CAPTCHA + uuid);
        redisTemplate.delete(RedisConstants.KEY_PREFIX_LOGIN_CAPTCHA + uuid);

        if (StringUtils.isEmpty(redisCaptcha)) {
            throw new LoginException(BizExceptionEnum.INVALID_CAPTCHA);
        }

        if (!redisCaptcha.equalsIgnoreCase(captcha)) {
            throw new LoginException(BizExceptionEnum.INVALID_CAPTCHA);
        }
    }

}
