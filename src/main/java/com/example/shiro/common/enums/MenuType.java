package com.example.shiro.common.enums;

/**
 * 菜单类型
 * @Author: yaokui
 * @Date: 2019/6/3
 */
public enum MenuType {

    OTHER(0, "其他"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮")
    ;

    int code;
    String message;

    MenuType(int code, String message) {
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

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (MenuType s : MenuType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
