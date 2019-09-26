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
public class LoginReqDTO extends BaseDTO {

    /**
     * 用户名
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码标识
     */
    private String uuid;

    /**
     * 验证码
     */
    private String captcha;

}
