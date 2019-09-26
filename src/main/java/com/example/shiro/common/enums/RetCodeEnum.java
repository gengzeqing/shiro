package com.example.shiro.common.enums;

import lombok.Getter;

@Getter
public enum RetCodeEnum {

    PROCESSING("100", "处理中"),
    /*操作成功代表业务意义上的成功，假如是列表查询，则必须有结果；假如是删除操作，则业务必须删除指定记录等等*/
    SUCCESS("200", "操作成功"),
    FAILED("201", "操作失败"),
    REDIRECT("301", "重定向"),
    VALIDATE_FAILED("401", "参数验证失败"),
    /*记录不存在只是在查询的时候出现，代表一种失败状态*/
    DATA_NOT_FOUND("402", "记录不存在"),
    SYSTEM_ERROR("500", "操作异常"),
    REPEAT_REQUEST("999", "重复请求"),

    /*各系统业务code从1000开始*/
    NEED_LOGIN("1010", "未登录"),

    TOKEN_CREATE_FAILED("2001", "TOKEN生成失败"),
    TOKEN_EXPIRED("2002", "TOKEN已过期"),
    TOKEN_INVALID("2003", "TOKEN校验失败"),
    TOKEN_SSO_INVALID("2004", "已单点登录原TOKEN失效"),
    ;

    private String code;
    private String message;

    RetCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
