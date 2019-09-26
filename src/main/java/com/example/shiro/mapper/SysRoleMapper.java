package com.example.shiro.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface SysRoleMapper extends CommonMapper<SysRole> {

    /**
     * 根据角色ID列表查询角色信息列表
     * @param roleIds
     * @return
     */
    List<SysRole> findRolesByIds(@Param("roleIds") String roleIds);

    /**
     * 查询所有可用角色列表
     * @return
     */
    List<SysRole> queryList();

    /**
     * 分页查询角色列表
     * @param page
     * @param condition
     * @return
     */
    List<SysRole> queryPage(Page<SysRole> page, Map<String, Object> condition);

    /**
     * 按ID删除角色
     * @param roleId
     * @param deleted
     */
    void deleteRoleById(@Param("roleId") Long roleId, @Param("deleted") int deleted);
}
