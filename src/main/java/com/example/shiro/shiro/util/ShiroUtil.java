package com.example.shiro.shiro.util;

import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.exception.BusinessException;
import com.example.shiro.model.dto.LoginAdminResDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * @author wangzhiliang
 */
@Component
public class ShiroUtil {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取封装的 ShiroUser
     *
     * @return ShiroUser
     */
    public static LoginAdminResDTO getUser() {
        if (isGuest()) {
            return null;
        } else {
            return (LoginAdminResDTO) getSubject().getPrincipals().getPrimaryPrincipal();
        }
    }

    public static <T> T getEntity() {
        return (T) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 认证通过或已记住的用户。与guset搭配使用。
     *
     * @return 用户：true，否则 false
     */
    public static boolean isUser() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }

    /**
     * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
     *
     * @return 访客：true，否则false
     */
    public static boolean isGuest() {
        return !isUser();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getCaptcha(String key) {
        Object captcha = getSessionAttribute(key);
        if (captcha == null) {
            throw new BusinessException(BizExceptionEnum.INVALID_CAPTCHA);
        }
        getSession().removeAttribute(key);
        return captcha.toString();
    }

}
