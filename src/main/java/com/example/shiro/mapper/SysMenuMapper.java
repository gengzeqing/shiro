package com.example.shiro.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface SysMenuMapper extends CommonMapper<SysMenu> {

    /**
     * 根据菜单标识查询菜单信息
     * @param menuCode
     * @param menuId
     * @return
     */
    int getCountByCode(@Param("menuCode") String menuCode, @Param("menuId") Long menuId);

    /**
     * 根据菜单ID列表查询菜单信息列表
     * @param menuIds
     * @return
     */
    List<SysMenu> findByMenuIds(@Param("menuIds") String menuIds);

    /**
     * 分页查询菜单列表
     * @param page
     * @param condition
     * @return
     */
    List<SysMenu> queryPage(Page<SysMenu> page, Map<String, Object> condition);

    /**
     * 查询所有可用菜单列表
     *
     * @return
     * @param menuType
     */
    List<SysMenu> queryMenuList(@Param("menuType") int menuType);

    /**
     * 按ID删除菜单
     * @param menuId
     * @param deleted
     */
    void deleteMenuById(@Param("menuId") Long menuId, @Param("deleted") int deleted);

    /**
     * 计数子菜单个数
     * @param menuId
     * @param code
     * @return
     */
    int getCountByParentId(@Param("menuId") Long menuId, @Param("menuId")Integer code);

    /**
     * 查询所有可用权限列表
     * @return
     */
    List<SysMenu> queryAllMenuList();
}
