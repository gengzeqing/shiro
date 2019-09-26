package com.example.shiro.model.dto;

import com.example.shiro.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lvzhaopeng
 * @date 2019/4/28
 */
@Getter
@Setter
public class ChangePwdReqDTO extends BaseDTO {

    private static final long serialVersionUID = 7443861600183549442L;

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String reNewPassword;

}
