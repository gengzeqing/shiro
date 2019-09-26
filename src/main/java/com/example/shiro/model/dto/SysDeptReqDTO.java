package com.example.shiro.model.dto;

import com.example.shiro.model.base.PageReq;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
@Getter
@Setter
public class SysDeptReqDTO extends PageReq {

    /**
     * 部门全称
     */
    private String fullName;

}
