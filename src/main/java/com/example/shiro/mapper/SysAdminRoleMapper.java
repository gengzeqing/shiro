package com.example.shiro.mapper;

import com.example.shiro.model.sys.SysAdminRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author Generator
 * @since 2019-06-04
 */
public interface SysAdminRoleMapper extends CommonMapper<SysAdminRole> {

    /**
     * 查询用户绑定的角色ID列表
     * @param accountId
     * @return
     */
    List<Long> findRoleIdsByUserId(@Param("accountId") Long accountId);

    /**
     * 删除原绑定角色
     * @param accountId
     */
    void deleteByUserId(@Param("accountId") Long accountId);

    /**
     * 根据角色ID查询管理员用户ID列表
     * @param roleId
     * @return
     */
    List<Long> findUserIdsByRoleId(@Param("roleId") Long roleId);
}
