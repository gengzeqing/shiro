package com.example.shiro.model.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Getter
@Setter
public class SysAdminForm implements Serializable {

    private static final long serialVersionUID = -5316881793124062658L;
    /**
     * 账号ID
     */
    private Long accountId;
    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    private String cnName;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话
     */
    private String phoneNum;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 状态(0：冻结  1：启用）
     */
    private Integer status = 1;
    /**
     * 删除(0：未删除  1：已删除）
     */
    private Integer deleted = 0;
}
