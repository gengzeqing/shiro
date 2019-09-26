package com.example.shiro.service.shiro;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface ISysRoleMenuService {

    /**
     * 根据角色ID列表获取角色绑定的菜单列表
     * @param roleIds
     * @return
     */
    List<Long> findMenuIdsByRoleIds(String roleIds);

    /**
     * 计数菜单被关联角色个数
     * @param menuId
     * @return
     */
    int getCountByMenuId(Long menuId);

    /**
     * 分配权限
     * @param roleId
     * @param menuIdList
     */
    void bindMenus(Long roleId, List<Long> menuIdList);
}
