package com.example.shiro.controller.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.common.constants.Administrator;
import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.controller.BaseController;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.model.base.PageResp;
import com.example.shiro.model.base.Result;
import com.example.shiro.model.builder.SysMenuBuilder;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.model.dto.SysMenuReqDTO;
import com.example.shiro.model.dto.SysMenuResDTO;
import com.example.shiro.model.form.SysMenuForm;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.service.shiro.ISysAdminRoleService;
import com.example.shiro.service.shiro.ISysMenuService;
import com.example.shiro.service.shiro.ISysRoleMenuService;
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

import java.util.List;

/**
 * 系统菜单控制器
 *
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Slf4j
@RestController
@RequestMapping("/ms/authority/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysAdminRoleService sysAdminRoleService;

    /**
     * 菜单分页列表
     * @param sysMenuReqDTO
     * @return
     */
    @PostMapping("/list")
    public Result<PageResp<SysMenuResDTO>> queryPage(@RequestBody SysMenuReqDTO sysMenuReqDTO) {
        Page<SysMenu> page = sysMenuReqDTO.toPage();
        Page<SysMenu> sysMenuPage = sysMenuService.queryPage(page);
        List<SysMenu> sysMenuList = sysMenuPage.getRecords();

        List<SysMenuResDTO> sysMenuResDTOList = Lists.newArrayList();
        sysMenuList.forEach(sysMenu -> sysMenuResDTOList.add(SysMenuBuilder.modelToDto(sysMenu)));

        Result<PageResp<SysMenuResDTO>> sysMenuResPage= PageResp.success(sysMenuPage,sysMenuResDTOList);
        return sysMenuResPage;
    }

    /**
     * 菜单列表 (下拉)
     *
     * @return
     */
    @GetMapping("/allList")
    public Result menuList() {
        // 查询所有可用菜单列表
        List<SysMenu> sysMenuList;
        LoginAdminResDTO loginAdminResDTO = getCurrentUser();
        if (loginAdminResDTO == null) {
            throw new BusinessException(BizExceptionEnum.SESSION_TIMEOUT);
        }

        if (Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                || Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                || Administrator.LZBX_ADMIN_ID.equals(loginAdminResDTO.getAccountId())) {
            sysMenuList = sysMenuService.queryAllMenuList();
        } else {
            // 查询用户绑定的角色ID列表
            List<Long> roleIdList = sysAdminRoleService.findRoleIdsByUserId(loginAdminResDTO.getAccountId());
            // 根据角色ID列表获取角色绑定的菜单列表
            List<Long> menuIdList = sysRoleMenuService.findMenuIdsByRoleIds(StrKit.listToStr(roleIdList));
            // 根据菜单ID列表查询菜单信息列表
            sysMenuList = sysMenuService.findByMenuIds(StrKit.listToStr(menuIdList));
        }

        List<SysMenuResDTO> sysMenuResDTOList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (!Administrator.SYS_ADMIN_ID.equals(loginAdminResDTO.getAccountId()) && !Administrator.DHPH_ADMIN_ID.equals(loginAdminResDTO.getAccountId())
                    && (sysMenu.getId() == 10 || sysMenu.getParentId() == 10)) {
                continue;
            }
            sysMenuResDTOList.add(SysMenuBuilder.modelToDto(sysMenu));
        }

        List<SysMenuResDTO> menuList = SysMenuBuilder.buildTree(sysMenuResDTOList);

        return Result.success(menuList);
    }

    /**
     * 添加菜单
     *
     * @param sysMenuForm
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody SysMenuForm sysMenuForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysMenuForm.getMenuCode(), sysMenuForm.getMenuName(), sysMenuForm.getMenuType())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        int menuCount = sysMenuService.getCountByCode(sysMenuForm.getMenuCode(), null);
        if (menuCount > 0) {
            throw new BusinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        SysMenu sysMenu = SysMenuBuilder.createModel(sysMenuForm);
        // 保存菜单信息
        sysMenuService.createMenu(sysMenu);
        log.info("添加菜单成功");

        return Result.success();
    }

    /**
     * 更新菜单 查询菜单
     *
     * @param menuId
     * @return
     */
    @GetMapping("/forUpdate/{menuId}")
    public Result forUpdate(@PathVariable Long menuId) {
        //校验参数
        if (menuId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysMenu sysMenu = sysMenuService.selectById(menuId);
        if (sysMenu == null) {
            throw new BusinessException(BizExceptionEnum.MENU_NOT_EXISTED);
        }
        
        SysMenuResDTO sysMenuResDTO = SysMenuBuilder.modelToDto(sysMenu);
        return Result.success(sysMenuResDTO);
    }

    /**
     * 修改菜单
     *
     * @param sysMenuForm
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysMenuForm sysMenuForm) {
        //校验参数
        if (ParamsCheckUtils.isNull(sysMenuForm.getMenuId(), sysMenuForm.getMenuCode(), sysMenuForm.getMenuName(), sysMenuForm.getMenuType())) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        int menuCount = sysMenuService.getCountByCode(sysMenuForm.getMenuCode(), sysMenuForm.getMenuId());
        if (menuCount > 0) {
            throw new BusinessException(BizExceptionEnum.EXISTED_THE_MENU);
        }
        
        SysMenu sysMenu = sysMenuService.selectById(sysMenuForm.getMenuId());
        sysMenu = SysMenuBuilder.updateModel(sysMenuForm, sysMenu);

        // 修改菜单提交
        sysMenuService.updateMenu(sysMenu);
        log.info("修改菜单成功");

        return Result.success();
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @GetMapping("/delete/{menuId}")
    public Result delete(@PathVariable Long menuId) {
        //校验参数
        if (menuId == null) {
            throw new BusinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }

        SysMenu sysMenu = sysMenuService.selectById(menuId);
        if (sysMenu == null) {
            throw new BusinessException(BizExceptionEnum.MENU_NOT_EXISTED);
        }

        int childCount = sysMenuService.getCountByParentId(menuId);
        if (childCount > 0) {
            throw new BusinessException(BizExceptionEnum.DELETE_MENU_HAS_CHILD);
        }

        int usedCount = sysRoleMenuService.getCountByMenuId(menuId);
        if (usedCount > 0) {
            throw new BusinessException(BizExceptionEnum.DELETE_MENU_HAS_USED);
        }

        // 删除菜单
        sysMenuService.deleteById(menuId);

        return Result.success();
    }

}
