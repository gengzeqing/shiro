package com.example.shiro.model.builder;

import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.model.dto.SysDeptResDTO;
import com.example.shiro.model.form.SysDeptForm;
import com.example.shiro.model.sys.SysDept;
import com.example.shiro.utils.DateUtils;

import java.util.Date;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
public class SysDeptBuilder {

    public static SysDeptResDTO modelToDto(SysDept copy) {
        SysDeptResDTO sysDeptResDTO = new SysDeptResDTO();
        sysDeptResDTO.setDeptId(copy.getId());
        sysDeptResDTO.setParentId(copy.getParentId());
        sysDeptResDTO.setSimpleName(copy.getSimpleName());
        sysDeptResDTO.setFullName(copy.getFullName());
        sysDeptResDTO.setPriority(copy.getPriority());
        sysDeptResDTO.setTips(copy.getTips());
        sysDeptResDTO.setCreateTime(DateUtils.format(copy.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));

        return sysDeptResDTO;
    }

    public static SysDept createModel(SysDeptForm copy) {
        SysDept sysDept = new SysDept();
        sysDept.setParentId(copy.getParentId());
        sysDept.setSimpleName(copy.getSimpleName());
        sysDept.setFullName(copy.getFullName());
        sysDept.setPriority(copy.getPriority());
        sysDept.setTips(copy.getTips());
        sysDept.setDeleted(DeletedStatus.UN_DELETED.getCode());
        sysDept.setCreateTime(new Date());
        sysDept.setModifyTime(new Date());

        return sysDept;
    }

    public static SysDept updateModel(SysDeptForm copy, SysDept sysDept) {
        sysDept.setParentId(copy.getParentId());
        sysDept.setSimpleName(copy.getSimpleName());
        sysDept.setFullName(copy.getFullName());
        sysDept.setPriority(copy.getPriority());
        sysDept.setTips(copy.getTips());
        sysDept.setModifyTime(new Date());

        return sysDept;
    }
}
