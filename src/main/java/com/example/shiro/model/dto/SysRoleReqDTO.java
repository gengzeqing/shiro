package com.example.shiro.model.dto;

import com.example.shiro.model.base.PageReq;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yaokui
 * @Date: 2019/6/11
 */
@Getter
@Setter
public class SysRoleReqDTO extends PageReq {

    private static final long serialVersionUID = -4331700330723062795L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;
}
