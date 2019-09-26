package com.example.shiro.model.builder;

import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.model.dto.SysRoleResDTO;
import com.example.shiro.model.form.SysRoleForm;
import com.example.shiro.model.sys.SysRole;
import com.example.shiro.utils.DateUtils;

import java.util.Date;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
public class SysRoleBuilder {

    public static SysRoleResDTO modelToDto(SysRole copy) {
        SysRoleResDTO sysRoleResDTO = new SysRoleResDTO();
        sysRoleResDTO.setRoleId(copy.getId());
        sysRoleResDTO.setRoleName(copy.getName());
        sysRoleResDTO.setRoleAlias(copy.getAlias());
        sysRoleResDTO.setPriority(copy.getPriority());
        sysRoleResDTO.setParentId(copy.getParentId());
        sysRoleResDTO.setDeptId(copy.getDeptId());
        sysRoleResDTO.setCreateTime(DateUtils.format(copy.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));

        return sysRoleResDTO;
    }

    public static SysRole createModel(SysRoleForm copy) {
        SysRole sysRole = new SysRole();
        sysRole.setName(copy.getRoleName());
        sysRole.setAlias(copy.getRoleAlias());
        sysRole.setPriority(copy.getPriority());
        sysRole.setParentId(copy.getParentId());
        sysRole.setDeptId(copy.getDeptId());
        sysRole.setDeleted(DeletedStatus.UN_DELETED.getCode());
        sysRole.setCreateTime(new Date());
        sysRole.setModifyTime(new Date());

        return sysRole;
    }

    public static SysRole updateModel(SysRoleForm copy, SysRole sysRole) {
        sysRole.setId(copy.getRoleId());
        sysRole.setName(copy.getRoleName());
        sysRole.setAlias(copy.getRoleAlias());
        sysRole.setPriority(copy.getPriority());
        sysRole.setParentId(copy.getParentId());
        sysRole.setDeptId(copy.getDeptId());
        sysRole.setModifyTime(new Date());

        return sysRole;
    }
}
