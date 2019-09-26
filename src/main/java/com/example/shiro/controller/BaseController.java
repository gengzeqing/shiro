package com.example.shiro.controller;

import com.example.shiro.model.dto.LoginAdminResDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * admin基础controller
 * @author chenhaili
 * @date 2019/7/2
 */
public class BaseController {

    public Long getCurrentUserId(){
        Subject currentUser = SecurityUtils.getSubject();
        LoginAdminResDTO principal = (LoginAdminResDTO)currentUser.getPrincipal();
        return principal.getAccountId();
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public LoginAdminResDTO getCurrentUser() {
        return (LoginAdminResDTO) SecurityUtils.getSubject().getPrincipal();
    }

}
