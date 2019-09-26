package com.example.shiro.service.shiro;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author Generator
 * @since 2019-06-04
 */
public interface ISysAdminRoleService {

    /**
     * 查询用户绑定的角色ID列表
     * @param accountId
     * @return
     */
    List<Long> findRoleIdsByUserId(Long accountId);

    /**
     * 分配角色
     * @param accountId
     * @param roleIdList
     */
    void bindRoles(Long accountId, List<Long> roleIdList);

    /**
     * 根据角色ID查询管理员用户ID列表
     * @param roleId
     * @return
     */
    List<Long> findUserIdsByRoleId(Long roleId);
}
