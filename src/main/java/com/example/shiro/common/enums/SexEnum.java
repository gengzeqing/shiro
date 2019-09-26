package com.example.shiro.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 性别
 * @Author: yaokui
 * @Date: 2019/6/3
 */
public enum SexEnum {

    DEFAULT(0, "默认"),
    MALE(1, "男"),
    FEMALE(2, "女")
    ;

    int code;
    String message;

    SexEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (SexEnum ms : SexEnum.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

    public static SexEnum getCodeByMsg(String message) {
        if (StringUtils.isNotEmpty(message)) {
            for (SexEnum sexEnum : SexEnum.values()) {
                if (sexEnum.getMessage().equals(message)) {
                    return sexEnum;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
