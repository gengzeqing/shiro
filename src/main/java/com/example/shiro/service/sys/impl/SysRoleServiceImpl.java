package com.example.shiro.service.sys.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.mapper.SysRoleMapper;
import com.example.shiro.mapper.SysRoleMenuMapper;
import com.example.shiro.model.sys.SysRole;
import com.example.shiro.service.sys.ISysRoleService;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysRole selectById(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        
        return sysRole;
    }

    @Override
    public List<SysRole> findRolesByIds(String roleIds) {
        List<SysRole> roleList = sysRoleMapper.findRolesByIds(roleIds);

        return roleList;
    }

    @Override
    public Page<SysRole> queryPage(Page<SysRole> page) {
        List<SysRole> sysRoleList = sysRoleMapper.queryPage(page, page.getCondition());

        page.setRecords(sysRoleList);
        return page;
    }

    @Override
    @Transactional
    public void createRole(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
    }

    @Override
    @Transactional
    public void updateRole(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public List<SysRole> queryRoleList() {
        List<SysRole> sysRoleList = sysRoleMapper.queryList();
        return sysRoleList;
    }

    @Override
    @Transactional
    public void deleteRoleById(Long roleId) {
        // 先删除角色关联的权限
        sysRoleMenuMapper.deleteByRoleId(roleId);
        // 删除角色
        sysRoleMapper.deleteRoleById(roleId, DeletedStatus.DELETED.getCode());
    }

}
