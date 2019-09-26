package com.example.shiro.model.dto;

import com.example.shiro.model.base.PageReq;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Getter
@Setter
public class SysAdminReqDTO extends PageReq {

    /**
     * 登录账户
     */
    private String account;

    /**
     * 姓名
     */
    private String cnName;

    /**
     * 角色
     */
    private Long roleId;
}
