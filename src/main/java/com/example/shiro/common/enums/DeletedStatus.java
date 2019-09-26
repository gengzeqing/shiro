package com.example.shiro.common.enums;

/**
 * 删除标识
 * @Author: yaokui
 * @Date: 2019/6/3
 */
public enum DeletedStatus {

    DELETED(1, "已删除"),
    UN_DELETED(0, "未删除")
    ;

    int code;
    String message;

    DeletedStatus(int code, String message) {
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
            for (DeletedStatus ms : DeletedStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
