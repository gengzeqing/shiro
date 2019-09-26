package com.example.shiro.model.dto;

import com.example.shiro.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yaokui
 * @since 2019-06-04
 */
@Getter
@Setter
public class LoginAdminResDTO extends BaseDTO {

    private static final long serialVersionUID = 2860936376017914859L;

    /**
     * 登陆token
     */
    private String token;

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
     * 状态
     */
    private Integer status;

    /**
     * 状态信息
     */
    private String statusMsg;

}
