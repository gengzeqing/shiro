package com.example.shiro.service.shiro.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.mapper.SysAdminRoleMapper;
import com.example.shiro.model.sys.SysAdminRole;
import com.example.shiro.service.shiro.ISysAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 文章管理表 服务实现类
 * </p>
 *
 * @author yaokui
 * @since 2019-06-04
 */
@Service
public class SysAdminRoleServiceImpl extends ServiceImpl<SysAdminRoleMapper, SysAdminRole> implements ISysAdminRoleService {

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Override
    public List<Long> findRoleIdsByUserId(Long accountId) {
        return sysAdminRoleMapper.findRoleIdsByUserId(accountId);
    }

    @Override
    @Transactional
    public void bindRoles(Long accountId, List<Long> roleIdList) {
        // 删除原绑定角色
        sysAdminRoleMapper.deleteByUserId(accountId);
        // 绑定角色信息
        if (roleIdList != null || roleIdList.size() > 0) {
            for (Long roleId : roleIdList) {
                SysAdminRole sysAdminRole = new SysAdminRole();
                sysAdminRole.setUserId(accountId);
                sysAdminRole.setRoleId(roleId);
                sysAdminRole.setDeleted(DeletedStatus.UN_DELETED.getCode());
                sysAdminRoleMapper.insert(sysAdminRole);
            }
        }
    }

    @Override
    public List<Long> findUserIdsByRoleId(Long roleId) {
        List<Long> userIdList = sysAdminRoleMapper.findUserIdsByRoleId(roleId);
        return userIdList;
    }
}
