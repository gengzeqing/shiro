package com.example.shiro.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.shiro.common.constants.NodeUserConstants;
import com.example.shiro.common.constants.RedisConstants;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.common.enums.EnableStatus;
import com.example.shiro.common.enums.RetCodeEnum;
import com.example.shiro.exception.BizException;
import com.example.shiro.mapper.SysAdminMapper;
import com.example.shiro.mapper.SysAdminRoleMapper;
import com.example.shiro.mapper.SysMenuMapper;
import com.example.shiro.mapper.SysRoleMenuMapper;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.sys.SysAdmin;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.service.sys.ISysAdminService;
import com.example.shiro.utils.DateUtils;
import com.example.shiro.utils.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 文章管理表 服务实现类
 * </p>
 *
 * @author yaokui
 * @since 2019-06-04
 */
@Slf4j
@Service
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements ISysAdminService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired(required = false)
    private RedisSessionDAO redisSessionDAO;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysAdmin getByAccount(String account) {
        SysAdmin sysAdmin = sysAdminMapper.getByAccount(account);

        return sysAdmin;
    }

    @Override
    public SysAdmin selectById(Long userId) {
        SysAdmin sysAdmin = sysAdminMapper.selectById(userId);

        return sysAdmin;
    }

    @Override
    public Result loginByAccount(String account, String password) {
        // 校验用户
        Result result = new Result();
        SysAdmin sysAdmin = sysAdminMapper.getByAccount(account);
        if (sysAdmin == null) {
            result.setCode(BizExceptionEnum.USER_NOT_EXISTED.getFriendlyCode());
            result.setMessage(BizExceptionEnum.USER_NOT_EXISTED.getFriendlyMsg());
            return result;
        }

        // 检查用户是否已被锁定
        String lockedRedisKey = RedisConstants.KEY_PREFIX_LOCKED_USERNAME + sysAdmin.getId();
        String lockedUsername = redisTemplate.opsForValue().get(lockedRedisKey);
        boolean locked = StringUtils.isNotBlank(lockedUsername);
        if (locked) {
            result.setCode(BizExceptionEnum.ACCOUNT_PWD_ERR_FROZEN.getFriendlyCode());
            result.setMessage(BizExceptionEnum.ACCOUNT_PWD_ERR_FROZEN.getFriendlyMsg());
            return result;
        }
        if (sysAdmin.getStatus().equals(EnableStatus.DISABLED.getCode())) {
            result.setCode(BizExceptionEnum.ACCOUNT_FROZEN.getFriendlyCode());
            result.setMessage(BizExceptionEnum.ACCOUNT_FROZEN.getFriendlyMsg());
            return result;
        }

        // 校验密码
        result = checkPassword(sysAdmin, password);
        if (RetCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            // 登录成功日志
            //LogManager.getLogInstance().executeLog(LogTaskFactory.loginLog(sysAdmin.getAccount(), HttpKit.getIp()));

            return Result.success(sysAdmin);
        }
        return result;
    }

    @Override
    public List<SysMenu> getLoginMenusByAccount(String account) {
        // 查询用户信息
        SysAdmin sysAdmin = sysAdminMapper.getByAccount(account);
        if (sysAdmin == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXISTED.getFriendlyMsg());
        }

        // 权限菜单
        List<Long> roleIdList = sysAdminRoleMapper.findRoleIdsByUserId(sysAdmin.getId());
        log.info("用户角色ID列表 roleIdList:{}", StrKit.listToStr(roleIdList));
        List<Long> menuIdList = sysRoleMenuMapper.findMenuIdsByRoleIds(StrKit.listToStr(roleIdList));
        List<SysMenu> menuList = sysMenuMapper.findByMenuIds(StrKit.listToStr(menuIdList));

        return menuList;
    }

    @Override
    public Page<SysAdmin> queryPage(Page<SysAdmin> page, Map<String, Object> conditions) {
        List<SysAdmin> dataList = sysAdminMapper.queryPage(page, conditions);
        page.setRecords(dataList);
        return page;
    }

    @Override
    public int countByAccount(String account, Long accountId) {
        return sysAdminMapper.countByAccount(account, accountId);
    }

    @Override
    @Transactional
    public void createAdmin(SysAdmin sysAdmin) {
        sysAdmin.setSalt(RandomStringUtils.randomAlphanumeric(NodeUserConstants.SALT_LENGTH));
        sysAdmin.setPassword(new SimpleHash(NodeUserConstants.HASH_ALGORITHM_NAME, sysAdmin.getPassword(), sysAdmin.getSalt(), NodeUserConstants.HASH_ITERATIONS).toString());
        sysAdminMapper.insert(sysAdmin);
    }

    @Override
    @Transactional
    public void updateAdmin(SysAdmin sysAdmin) {
        sysAdminMapper.updateById(sysAdmin);
    }

    @Override
    @Transactional
    public void deleteById(Long accountId) {
        // 先删除账号关联的角色
        sysAdminRoleMapper.deleteByUserId(accountId);

        // 删除账号
        sysAdminMapper.deleteAdminById(accountId, DeletedStatus.DELETED.getCode());

        // 删除账号，强制下线该账号
        logout(accountId);
        log.info("删除账号，强制下线该账号 accountId:{}", accountId);
    }

    @Override
    @Transactional
    public void updatePassword(Long accountId, String newPassword) {
        SysAdmin sysAdmin = sysAdminMapper.selectById(accountId);
        sysAdmin.setPassword(new SimpleHash(NodeUserConstants.HASH_ALGORITHM_NAME, newPassword, sysAdmin.getSalt(), NodeUserConstants.HASH_ITERATIONS).toString());

        sysAdminMapper.updatePassword(accountId, sysAdmin.getPassword(), sysAdmin.getSalt());

        // 更新密码操作成功后，清除redis账户锁定
        redisTemplate.delete(RedisConstants.KEY_PREFIX_LOCKED_USERNAME + sysAdmin.getId());
        redisTemplate.delete(RedisConstants.KEY_PREFIX_PASSWORD_ERROR_CURRENT_COUNT + sysAdmin.getAccount());
        log.info("更新密码成功 accountId:{1}, newPassword:{2}", accountId, newPassword);

    }

    @Override
    @Transactional
    public void updateValid(Long accountId, int enableStatus) {
        sysAdminMapper.updateValid(accountId, enableStatus);

        // 冻结账号，强制下线该账号
        if (EnableStatus.DISABLED.getCode() == enableStatus) {
            logout(accountId);
            log.info("冻结账号，强制下线该账号 accountId:{}", accountId);
        }

    }

    @Override
    public boolean checkOldPwd(Long accountId, String oldPassword) {
        SysAdmin sysAdmin = sysAdminMapper.selectById(accountId);
        String newPassword = new SimpleHash(NodeUserConstants.HASH_ALGORITHM_NAME, oldPassword, sysAdmin.getSalt(), NodeUserConstants.HASH_ITERATIONS).toString();
        return sysAdmin.getPassword().equals(newPassword);
    }

    private boolean validPassword(SysAdmin userInfo, String password) {
        String newPassword = new SimpleHash(NodeUserConstants.HASH_ALGORITHM_NAME, password, userInfo.getSalt(), NodeUserConstants.HASH_ITERATIONS).toString();
        return userInfo.getPassword().equals(newPassword);
    }

    /**
     * 检查密码
     *
     * @param sysAdmin
     * @param password
     */
    private Result checkPassword(SysAdmin sysAdmin, String password) {
        // 密码错误次数key
        Result result = new Result();
        String errorPwdCountKey = RedisConstants.KEY_PREFIX_PASSWORD_ERROR_CURRENT_COUNT + sysAdmin.getAccount();

        // 校验密码
        boolean checkFlag = validPassword(sysAdmin, password);
        if (checkFlag) {
            // 密码校验成功，删除redis缓存密码错误次数
            redisTemplate.delete(errorPwdCountKey);
            return Result.success();
        }

        // 密码错误
        String redisValue = redisTemplate.opsForValue().get(errorPwdCountKey);
        int errorCount = StringUtils.isNoneBlank(redisValue) ? Integer.valueOf(redisValue) : 0;
        errorCount++;
        redisTemplate.opsForValue().set(errorPwdCountKey, errorCount + "");
        if (errorCount < RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT) {
            String errorMsg = "密码不正确,还可输入" + (RedisConstants.KEY_PASSWORD_ERROR_MAX_COUNT - errorCount) + "次";
            result.setCode(BizExceptionEnum.ERROR_ACCOUNT_PWD.getFriendlyCode());
            result.setMessage(errorMsg);
            return result;
        }

        String dataStr = DateUtils.format(new Date(), DateUtils.YYYY_MM_DD) + " 23:59:59";
        long expire = DateUtils.parse(dataStr, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(RedisConstants.KEY_PREFIX_LOCKED_USERNAME + sysAdmin.getId(), sysAdmin.getAccount(), expire, TimeUnit.MILLISECONDS);
        result.setCode(BizExceptionEnum.ACCOUNT_PWD_ERR_FROZEN.getFriendlyCode());
        result.setMessage(BizExceptionEnum.ACCOUNT_PWD_ERR_FROZEN.getFriendlyMsg());
        return result;
    }

    /**
     * 强制下线某账户
     *
     * @param accountId 账号ID
     */
    protected void logout(Long accountId) {
        if (accountId == null) {
            log.error("账号ID为NULL");
            return;
        }
        Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
        if (CollectionUtils.isEmpty(activeSessions)) {
            log.warn("当前无登录信息");
            return;
        }
        for (Session activeSession : activeSessions) {
            PrincipalCollection existingPrincipals =
                    (PrincipalCollection) activeSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (CollectionUtils.isEmpty(existingPrincipals)) {
                continue;
            } else {
//                LoginAdminResDTO loginAdminResDTO = (LoginAdminResDTO) existingPrincipals.getPrimaryPrincipal();
//                System.out.println(accountId.equals(loginAdminResDTO.getAccountId()));
//                if (accountId.equals(loginAdminResDTO.getAccountId())) {
//                    redisSessionDAO.delete(activeSession);
//                }
                /// 强制转换成实体 类型错误 具体原因不明 替代方法 转换成JSON
                log.info("强制下线某账户 getPrimaryPrincipal {};" + JSON.toJSONString(existingPrincipals.getPrimaryPrincipal()) + "accountId:{}" + accountId);
                String jsonString = JSON.toJSONString(existingPrincipals.getPrimaryPrincipal());
                JSONObject json = JSONObject.fromObject(jsonString);
                System.out.println(accountId.equals(json.getString("accountId")));
                if (accountId.equals(json.getString("accountId"))) {
                    redisSessionDAO.delete(activeSession);
                }
            }
        }
    }
}
