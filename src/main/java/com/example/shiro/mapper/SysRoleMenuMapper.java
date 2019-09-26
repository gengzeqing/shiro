package com.example.shiro.mapper;

import com.example.shiro.model.sys.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface SysRoleMenuMapper extends CommonMapper<SysRoleMenu> {

    /**
     * 根据角色ID列表获取角色绑定的菜单列表
     * @param roleIds
     * @return
     */
    List<Long> findMenuIdsByRoleIds(@Param("roleIds") String roleIds);

    /**
     * 计数菜单关联的角色个数
     * @param menuId
     * @return
     */
    int getCountByMenuId(@Param("menuId") Long menuId);

    /**
     * 按角色ID删除权限关联
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);
}
