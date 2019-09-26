package com.example.shiro.service.shiro;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface ISysMenuService {

    /**
     * 根据ID查询菜单信息
     * @param menuId
     * @return
     */
    SysMenu selectById(Long menuId);

    /**
     * 根据菜单标识查询菜单信息
     * @param menuCode
     * @param menuId
     * @return
     */
    int getCountByCode(String menuCode, Long menuId);

    /**
     * 根据菜单ID列表查询菜单信息列表
     * @param menuIds
     * @return
     */
    List<SysMenu> findByMenuIds(String menuIds);

    /**
     * 分页查询菜单列表
     * @param page
     * @return
     */
    Page<SysMenu> queryPage(Page<SysMenu> page);

    /**
     * 查询所有可用菜单列表
     * MenuType.MENU
     * MenuType.BUTTON
     * @return
     */
    List<Map<String,Object>> queryMenuList(int menuType);

    /**
     * 查询所有可用权限列表
     * @return
     */
    List<SysMenu> queryAllMenuList();

    /**
     * 保存菜单信息
     * @param sysMenu
     */
    void createMenu(SysMenu sysMenu);

    /**
     * 修改菜单
     * @param sysMenu
     */
    void updateMenu(SysMenu sysMenu);

    /**
     * 按ID删除
     * @param menuId
     */
    void deleteById(Long menuId);

    /**
     * 计数子菜单个数
     * @param menuId
     * @return
     */
    int getCountByParentId(Long menuId);

}
