package com.example.shiro.model.builder;

import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.common.enums.EnableStatus;
import com.example.shiro.common.enums.SexEnum;
import com.example.shiro.model.dto.SysAdminResDTO;
import com.example.shiro.model.form.SysAdminForm;
import com.example.shiro.model.sys.SysAdmin;
import com.example.shiro.utils.DateUtils;

import java.util.Date;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
public class SysAdminBuilder {

    public static SysAdminResDTO modelToDto(SysAdmin copy) {
        SysAdminResDTO sysAdminResDTO = new SysAdminResDTO();
        sysAdminResDTO.setAccountId(copy.getId());
        sysAdminResDTO.setAccount(copy.getAccount());
        sysAdminResDTO.setCnName(copy.getCnName());
        sysAdminResDTO.setSex(copy.getSex());
        sysAdminResDTO.setSexMsg(SexEnum.valueOf(copy.getSex()));
        sysAdminResDTO.setPhoneNum(copy.getPhone());
        sysAdminResDTO.setEmail(copy.getEmail());
        sysAdminResDTO.setDeptId(copy.getDeptId());
        sysAdminResDTO.setStatus(copy.getStatus());
        sysAdminResDTO.setStatusMsg(EnableStatus.valueOf(copy.getStatus()));
        sysAdminResDTO.setCreateTime(DateUtils.format(copy.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));

        return sysAdminResDTO;
    }

    public static SysAdmin createModel(SysAdminForm copy) {
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setAccount(copy.getAccount());
        sysAdmin.setCnName(copy.getCnName());
        sysAdmin.setPassword(copy.getPassword());
        sysAdmin.setSex(copy.getSex());
        sysAdmin.setEmail(copy.getEmail());
        sysAdmin.setPhone(copy.getPhoneNum());
        sysAdmin.setDeptId(copy.getDeptId());
        sysAdmin.setStatus(EnableStatus.ENABLED.getCode());
        sysAdmin.setDeleted(DeletedStatus.UN_DELETED.getCode());
        sysAdmin.setCreateTime(new Date());
        sysAdmin.setModifyTime(new Date());

        return sysAdmin;
    }

    public static SysAdmin updateModel(SysAdminForm copy, SysAdmin sysAdmin) {
        // 更新字段
        sysAdmin.setAccount(copy.getAccount());
        sysAdmin.setCnName(copy.getCnName());
        sysAdmin.setSex(copy.getSex());
        sysAdmin.setEmail(copy.getEmail());
        sysAdmin.setPhone(copy.getPhoneNum());
        sysAdmin.setDeptId(copy.getDeptId());
        sysAdmin.setModifyTime(new Date());

        return sysAdmin;
    }
}
