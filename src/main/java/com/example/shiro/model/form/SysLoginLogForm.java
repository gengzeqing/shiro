package com.example.shiro.model.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: yaokui
 * @Date: 2019/6/11
 */
@Getter
@Setter
public class SysLoginLogForm implements Serializable {

    private static final long serialVersionUID = 6768922379812188167L;

    /**
     * 管理员账户
     */
    private String account;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 备注信息
     */
    private String message;

    /**
     * IP地址
     */
    private String ip;

}
