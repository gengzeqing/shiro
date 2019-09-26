package com.example.shiro.controller.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.common.constants.Administrator;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.controller.BaseController;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.model.base.PageResp;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.builder.SysRoleBuilder;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.model.dto.SysRoleReqDTO;
import com.example.shiro.model.dto.SysRoleResDTO;
import com.example.shiro.model.form.SysRoleForm;
import com.example.shiro.model.sys.SysDept;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.model.sys.SysRole;
import com.example.shiro.service.shiro.ISysAdminRoleService;
import com.example.shiro.service.shiro.ISysMenuService;
import com.example.shiro.service.shiro.ISysRoleMenuService;
import com.example.shiro.service.sys.ISysDeptService;
import com.example.shiro.service.sys.ISysRoleService;
import com.example.shiro.utils.ParamsCheckUtils;
import com.example.shiro.utils.StrKit;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色控制器
 *
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Slf4j
@RestController
@RequestMapping("/ms/authority/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysAdminRoleService sysAdminRoleService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysMenuService sysMenuService;
    /**
     * 角色分页列表
     * @param sysRoleReqDTO
     * @return
     */
    @PostMapping("/list")
    public Result<PageResp<SysRoleResDTO>> queryPage(@RequestBody SysRoleReqDTO sysRoleReqDTO) {
        Page<SysRole> page = sysRoleReqDTO.toPage();
        Page<SysRole> sysRolePage = sysRoleService.queryPage(page);

        List<SysRole> sysRoleList = sysRolePage.getRecords();
        List<SysRoleResDTO> sysRoleResDTOList = Lists.newArrayList();
        sysRoleList.forEach(sysRole -> {
            SysDept sysDept = sysDeptService.selectById(sysRole.getDeptId());
            SysRoleResDTO sysRoleResDTO = SysRoleBuilder.modelToDto(sysRole);
            sysRoleResDTO.setDeptName(sysDept.getFullName());
            sysRoleResDTOList.add(sysRoleResDTO);
        });

        Result<PageResp<SysRoleResDTO>> sysRoleResPage = PageResp.success(sysRolePage,sysRoleResDTOList);
        return sysRoleResPage;
    }

    /**
     * 角色列表 (下拉)
     *
     * @return
     */
    @GetMapping("/allList")
    public Result roleList() {
        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        // 查询所有可用角色列表
        List<SysRole> sysRoleList = sysRoleService.queryRoleList();
        List<Map<String, Object>> roleList = Lists.newArrayList();
        for (SysRole sysRole : sysRoleList) {
            if (!Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && !Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && !Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId())) {
                if (Administrator.ADMIN_ROLE_ID.equals(sysRole.getId())) {
                    continue;
                }
            }
            Map<String, Object> param = new HashMap<>();
            param.put("roleId", sysRole.getId());
            param.put("parentId", sysRole.getParentId());
            param.put("roleName", sysRole.getName());
            roleList.add(param);
        }

        return Result.success(roleList);
    }

    /**
     * 添加角色
     *
     * @param sysRoleForm
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody SysRoleForm sysRoleForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysRoleForm.getRoleName(), sysRoleForm.getDeptId())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysRole sysRole = SysRoleBuilder.createModel(sysRoleForm);
        // 保存角色信息
        sysRoleService.createRole(sysRole);
        log.info("添加角色成功");

        return Result.success();
    }

    /**
     * 更新角色 查询角色
     *
     * @param roleId
     * @return
     */
    @GetMapping("/forUpdate/{roleId}")
    public Result forUpdate(@PathVariable Long roleId) {
        //校验参数
        if (roleId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysRole sysRole = sysRoleService.selectById(roleId);
        if (sysRole == null) {
            throw new BusinessException(BizExceptionEnum.ROLE_NOT_EXISTED);
        }

        SysRoleResDTO sysRoleResDTO = SysRoleBuilder.modelToDto(sysRole);
        return Result.success(sysRoleResDTO);
    }

    /**
     * 修改角色
     *
     * @param sysRoleForm
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysRoleForm sysRoleForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysRoleForm.getRoleId(), sysRoleForm.getRoleName(), sysRoleForm.getDeptId())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (Administrator.ADMIN_ROLE_ID.equals(sysRoleForm.getRoleId()) && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_CHANGE_ADMIN_ROLE);
        }

        SysRole sysRole = sysRoleService.selectById(sysRoleForm.getRoleId());
        sysRole = SysRoleBuilder.updateModel(sysRoleForm, sysRole);
        // 修改角色提交
        sysRoleService.updateRole(sysRole);
        log.info("添加管理员用户成功");

        return Result.success();
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @GetMapping("/delete/{roleId}")
    public Result delete(@PathVariable Long roleId) {
        //校验参数
        if (roleId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (Administrator.ADMIN_ROLE_ID.equals(roleId)) {
            throw new BusinessException(BizExceptionEnum.CANT_DELETE_ADMIN_ROLE);
        }

        List<Long> userIdList = sysAdminRoleService.findUserIdsByRoleId(roleId);
        if (userIdList != null && userIdList.size() > 0) {
            throw new BusinessException(BizExceptionEnum.DELETE_ROLE_HAS_USED);
        }

        // 删除角色
        sysRoleService.deleteRoleById(roleId);

        return Result.success();
    }

    /**
     * 分配权限 查询菜单列表
     *
     * @return
     */
    @GetMapping("/forAllot/{roleId}")
    public Result forAllot(@PathVariable Long roleId) {
        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        // 查询角色关联的权限ID列表
        List<Long> roleIdList = Lists.newArrayList(roleId);
        List<Long> roleMenuIdList = sysRoleMenuService.findMenuIdsByRoleIds(StrKit.listToStr(roleIdList));
        List<SysMenu> sysMenuList = sysMenuService.findByMenuIds(StrKit.listToStr(roleMenuIdList));

        List<Long> menuIdList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (!Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && !Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && (sysMenu.getId() == 10 || sysMenu.getParentId() == 10)) {
                continue;
            }
            menuIdList.add(sysMenu.getId());

        }

        return Result.success(menuIdList);
    }

    /**
     * 分配权限提交
     *
     * @param roleId
     * @return
     */
    @PostMapping("/allot/{roleId}")
    public Result allot(@PathVariable Long roleId, @RequestBody List<Long> menuIdList) {
        // 校验参数
        if (roleId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (Administrator.ADMIN_ROLE_ID.equals(roleId) && !(Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId()))) {
            throw new BusinessException(BizExceptionEnum.CANT_ALLOT_ADMIN_ROLE);
        }

        SysRole sysRole = sysRoleService.selectById(roleId);
        if (sysRole == null) {
            throw new BusinessException(BizExceptionEnum.ROLE_NOT_EXISTED);
        }

        // 分配权限
        sysRoleMenuService.bindMenus(roleId, menuIdList);

        return Result.success();
    }

}
