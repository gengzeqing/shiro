package com.example.shiro.common.enums;

/**
 * 是否推荐
 * @Author: yaokui
 * @Date: 2019/6/3
 */
public enum YesNoStatus {

    YES(1, "是"),
    NO(0, "否")
    ;

    int code;
    String message;

    YesNoStatus(int code, String message) {
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
            for (YesNoStatus ms : YesNoStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
