package com.example.shiro.common.enums;

/**
 * 可用标识
 * @Author: yaokui
 * @Date: 2019/6/3
 */
public enum EnableStatus {

    ENABLED(1, "启用"),
    DISABLED(0, "冻结")
    ;

    int code;
    String message;

    EnableStatus(int code, String message) {
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
            for (EnableStatus ms : EnableStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
