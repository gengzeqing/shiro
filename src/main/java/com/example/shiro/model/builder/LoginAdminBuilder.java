package com.example.shiro.model.builder;

import com.example.shiro.common.enums.EnableStatus;
import com.example.shiro.common.enums.SexEnum;
import com.example.shiro.model.dto.LoginAdminResDTO;
import com.example.shiro.model.sys.SysAdmin;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
public class LoginAdminBuilder {

    public static LoginAdminResDTO createLoginUser(SysAdmin copy) {
        LoginAdminResDTO loginAdminResDTO = new LoginAdminResDTO();
        loginAdminResDTO.setAccountId(copy.getId());
        loginAdminResDTO.setAccount(copy.getAccount());
        loginAdminResDTO.setCnName(copy.getCnName());
        loginAdminResDTO.setSexMsg(SexEnum.valueOf(copy.getSex()));
        loginAdminResDTO.setPhoneNum(copy.getPhone());
        loginAdminResDTO.setEmail(copy.getEmail());
        loginAdminResDTO.setDeptId(copy.getDeptId());
        loginAdminResDTO.setStatusMsg(EnableStatus.valueOf(copy.getStatus()));

        return loginAdminResDTO;
    }
}
