package com.example.shiro.service.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.shiro.model.sys.SysRole;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
public interface ISysRoleService {

    /**
     * 根据角色ID查询角色信息
     * @param roleId
     * @return
     */
    SysRole selectById(Long roleId);

    /**
     * 根据角色ID列表查询角色信息列表
     * @param roleIds
     * @return
     */
    List<SysRole> findRolesByIds(String roleIds);

    /**
     * 分页查询角色列表
     * @param page
     * @return
     */
    Page<SysRole> queryPage(Page<SysRole> page);

    /**
     * 添加角色
     * @param sysRole
     */
    void createRole(SysRole sysRole);

    /**
     * 修改角色
     * @param sysRole
     */
    void updateRole(SysRole sysRole);

    /**
     * 查询所有可用角色列表
     * @return
     */
    List<SysRole> queryRoleList();

    /**
     * 按ID删除角色
     * @param roleId
     */
    void deleteRoleById(Long roleId);
}
