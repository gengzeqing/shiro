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
public class SysOperationLogForm implements Serializable {

    private static final long serialVersionUID = -4036153853604262621L;

    /**
     * 日志类型
     */
    private String logType;
    /**
     * 日志名称
     */
    private String logName;
    /**
     * 管理员id
     */
    private Long mUserId;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 备注信息
     */
    private String message;
    /**
     * IP地址
     */
    private String ip;

}
