package com.example.shiro.model.dto;

import com.example.shiro.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Getter
@Setter
public class SysAdminResDTO extends BaseDTO {

    private static final long serialVersionUID = 4831366834896104234L;

    /**
     * 用户ID
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
     * 性别
     */
    private Integer  sex;

    /**
     * 性别信息
     */
    private String  sexMsg;

    /**
     * 电话
     */
    private String phoneNum;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态信息
     */
    private String statusMsg;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 角色名称集 ("|"分隔)
     */
    private String roleNames;

}
