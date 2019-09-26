package com.example.shiro.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.common.constants.Administrator;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.common.enums.EnableStatus;
import com.example.shiro.controller.BaseController;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.model.base.PageResp;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.builder.SysAdminBuilder;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.model.dto.SysAdminReqDTO;
import com.example.shiro.model.dto.SysAdminResDTO;
import com.example.shiro.model.form.SysAdminForm;
import com.example.shiro.model.sys.SysAdmin;
import com.example.shiro.model.sys.SysDept;
import com.example.shiro.model.sys.SysRole;
import com.example.shiro.service.shiro.ISysAdminRoleService;
import com.example.shiro.service.sys.ISysAdminService;
import com.example.shiro.service.sys.ISysDeptService;
import com.example.shiro.service.sys.ISysRoleService;
import com.example.shiro.utils.ParamsCheckUtils;
import com.example.shiro.utils.StrKit;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统管理员控制器
 *
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Slf4j
@RestController
@RequestMapping("/ms/authority")
public class SysAdminController extends BaseController {

    @Autowired
    private ISysAdminService sysAdminService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysAdminRoleService sysAdminRoleService;

    /**
     * 管理员列表
     * @param sysAdminReqDTO
     * @return
     */
    @PostMapping("/user/list")
    public Result<PageResp<SysAdminResDTO>> queryPage(@RequestBody SysAdminReqDTO sysAdminReqDTO) {
        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        Page<SysAdmin> page = sysAdminReqDTO.toPage();
        Map<String, Object> conditions = page.getCondition();
        Long roleId = sysAdminReqDTO.getRoleId();
        if (roleId != null) {
            List<Long> userIdList = sysAdminRoleService.findUserIdsByRoleId(roleId);
            List<Long> accountIdList = Lists.newArrayList();
            for (Long accountId : userIdList) {
                if (Administrator.SYS_ADMIN_ID.equals(accountId) || Administrator.DHPH_ADMIN_ID.equals(accountId)) {
                    continue;
                }
                accountIdList.add(accountId);
            }
            conditions.put("userIds", StrKit.listToStr(accountIdList));
        }

        if (!Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())) {
            List<Long> accountIdNotInList = Lists.newArrayList();
            if (Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())) {
                accountIdNotInList = Lists.newArrayList(
                        Administrator.SYS_ADMIN_ID
                );
            } else if (!Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) && !Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())) {
                accountIdNotInList = Lists.newArrayList(
                        Administrator.SYS_ADMIN_ID,
                        Administrator.DHPH_ADMIN_ID
                );
            }
            conditions.put("userIdNotIn", StrKit.listToStr(accountIdNotInList));
        }

        Page<SysAdmin> sysAdminPage = sysAdminService.queryPage(page, conditions);

        List<SysAdmin> sysAdminList = sysAdminPage.getRecords();
        List<SysAdminResDTO> sysAdminResDTOList = Lists.newArrayList();
        sysAdminList.forEach(user -> {
            SysDept sysDept = sysDeptService.selectById(user.getDeptId());
            SysAdminResDTO sysAdminResDTO = SysAdminBuilder.modelToDto(user);
            sysAdminResDTO.setDeptName(sysDept.getFullName());
            List<Long> roleIds = sysAdminRoleService.findRoleIdsByUserId(user.getId());
            List<SysRole> sysRoleList = sysRoleService.findRolesByIds(StrKit.listToStr(roleIds));
            StringBuffer roleNameBuffer = new StringBuffer();
            int i = 0;
            for (SysRole sysRole : sysRoleList) {
                roleNameBuffer.append(sysRole.getName());
                i++;
                if (i < sysRoleList.size()) {
                    roleNameBuffer.append("|");
                }
            }
            sysAdminResDTO.setRoleNames(roleNameBuffer.toString());
            sysAdminResDTOList.add(sysAdminResDTO);
        });

        Result<PageResp<SysAdminResDTO>> sysAdminResPage = PageResp.success(sysAdminPage, sysAdminResDTOList);
        return sysAdminResPage;
    }

    /**
     * 添加管理员用户
     *
     * @param sysAdminForm
     * @return
     */
    @PostMapping("/user/create")
    public Result create(@RequestBody SysAdminForm sysAdminForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysAdminForm.getAccount(), sysAdminForm.getPassword())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        int count = sysAdminService.countByAccount(sysAdminForm.getAccount(), null);
        if (count > 0) {
            throw new BusinessException(BizExceptionEnum.USER_ALREADY_REG);
        }

        SysAdmin sysAdmin = SysAdminBuilder.createModel(sysAdminForm);
        // 保存管理员
        sysAdminService.createAdmin(sysAdmin);
        log.info("添加管理员账号成功");

        return Result.success();
    }

    /**
     * 更新管理员用户 查询用户信息
     *
     * @param accountId
     * @return
     */
    @GetMapping("/user/forUpdate/{accountId}")
    public Result forUpdate(@PathVariable Long accountId) {
        //校验参数
        if (accountId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(accountId);
        if (sysAdmin == null) {
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }

        SysAdminResDTO sysAdminResDTO = SysAdminBuilder.modelToDto(sysAdmin);
        return Result.success(sysAdminResDTO);
    }

    /**
     * 修改管理员提交
     *
     * @param sysAdminForm
     * @return
     */
    @PostMapping("/user/update")
    public Result update(@RequestBody SysAdminForm sysAdminForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysAdminForm.getAccount())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (loginAdminResDTO.getAccountId().equals(sysAdminForm.getAccountId())) {
            throw new BusinessException(BizExceptionEnum.CANT_CHANGE_SELF_ACCOUNT);
        }

        if ((Administrator.SYS_ADMIN_ID.equals(sysAdminForm.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(sysAdminForm.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(sysAdminForm.getAccountId()))
            && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }

        // 修改管理员用户account不能重复
        int count = sysAdminService.countByAccount(sysAdminForm.getAccount(), sysAdminForm.getAccountId());
        if (count > 0) {
            throw new BusinessException(BizExceptionEnum.USER_ALREADY_REG);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(sysAdminForm.getAccountId());
        sysAdmin = SysAdminBuilder.updateModel(sysAdminForm, sysAdmin);
        // 修改账号提交
        sysAdminService.updateAdmin(sysAdmin);
        log.info("添修改账号成功");

        return Result.success();
    }

    /**
     * 删除用户
     *
     * @param accountId
     * @return
     */
    @GetMapping("/delete/{accountId}")
    public Result delete(@PathVariable Long accountId) {
        //校验参数
        if (accountId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (loginAdminResDTO.getAccountId().equals(accountId)) {
            throw new BusinessException(BizExceptionEnum.CANT_DELETE_SELF_ACCOUNT);
        }

        if ((Administrator.SYS_ADMIN_ID.equals(accountId) || Administrator.DHPH_ADMIN_ID.equals(accountId) || Administrator.LZBX_ADMIN_ID.equals(accountId))
                && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(accountId);
        if (sysAdmin == null) {
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }

        // 删除账号
        sysAdminService.deleteById(accountId);
        log.info("删除账号操作成功");

        return Result.success();
    }

    /**
     * 重置密码
     *
     * @param accountId
     * @return
     */
    @GetMapping("/resetPwd/{accountId}")
    public Result resetPwd(@PathVariable Long accountId, String newPassword) {
        //校验参数
        if (accountId == null || StringUtils.isBlank(newPassword)) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if ((Administrator.SYS_ADMIN_ID.equals(accountId) || Administrator.DHPH_ADMIN_ID.equals(accountId) || Administrator.LZBX_ADMIN_ID.equals(accountId))
                && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_RESET_PWD_ADMIN);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(accountId);
        if (sysAdmin == null) {
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }

        // 重置密码
        sysAdminService.updatePassword(accountId, newPassword);
        log.info("重置密码操作成功");

        return Result.success();
    }

    /**
     * 冻结用户
     *
     * @param accountId
     * @return
     */
    @GetMapping("/frozen/{accountId}")
    public Result frozen(@PathVariable Long accountId) {
        //校验参数
        if (accountId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (loginAdminResDTO.getAccountId().equals(accountId)) {
            throw new BusinessException(BizExceptionEnum.CANT_FREEZE_SELF_ACCOUNT);
        }

        if ((Administrator.SYS_ADMIN_ID.equals(accountId) || Administrator.DHPH_ADMIN_ID.equals(accountId) || Administrator.LZBX_ADMIN_ID.equals(accountId))
                && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(accountId);
        if (sysAdmin == null) {
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }

        // 冻结账号
        sysAdminService.updateValid(accountId, EnableStatus.DISABLED.getCode());
        log.info("冻结账号操作成功");

        return Result.success();
    }

    /**
     * 解冻用户
     *
     * @param accountId
     * @return
     */
    @GetMapping("/unfrozen/{accountId}")
    public Result unfrozen(@PathVariable Long accountId) {
        //校验参数
        if (accountId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (loginAdminResDTO.getAccountId().equals(accountId)) {
            throw new BusinessException(BizExceptionEnum.CANT_UNFREEZE_SELF_ACCOUNT);
        }

        if ((Administrator.SYS_ADMIN_ID.equals(accountId) || Administrator.DHPH_ADMIN_ID.equals(accountId) || Administrator.LZBX_ADMIN_ID.equals(accountId))
                && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_UNFREEZE_ADMIN);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(accountId);
        if (sysAdmin == null) {
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }

        // 解冻账号
        sysAdminService.updateValid(accountId, EnableStatus.ENABLED.getCode());
        log.info("解冻账号操作成功");

        return Result.success();
    }

    /**
     * 分配角色 查询角色列表
     *
     * @return
     */
    @GetMapping("/user/forAllot/{accountId}")
    public Result forAllot(@PathVariable Long accountId) {
        // 查询用户关联的角色ID列表
        List<Long> userRoleIdList = sysAdminRoleService.findRoleIdsByUserId(accountId);

        return Result.success(userRoleIdList);
    }

    /**
     * 分配角色提交
     *
     * @param accountId
     * @return
     */
    @PostMapping("/user/allot/{accountId}")
    public Result allot(@PathVariable Long accountId, @RequestBody List<Long> roleIdList) {
        // 校验参数
        if (accountId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }
        log.info("分配角色 accountId {} :" + accountId + " 分配角色 roleIdList {}:" + JSON.toJSONString(roleIdList) + "分配角色 获取当前用户信息{}："+ loginAdminResDTO);
        if (loginAdminResDTO.getAccountId().equals(accountId)) {
            throw new BusinessException(BizExceptionEnum.CANT_ALLOT_SELF_ACCOUNT);
        }

        if ((Administrator.SYS_ADMIN_ID.equals(accountId) || Administrator.DHPH_ADMIN_ID.equals(accountId) || Administrator.LZBX_ADMIN_ID.equals(accountId))
                && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_ALLOT_ADMIN);
        }

        SysAdmin sysAdmin = sysAdminService.selectById(accountId);
        if (sysAdmin == null) {
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }

        // 角色分配
        sysAdminRoleService.bindRoles(accountId, roleIdList);

        return Result.success();
    }

}
