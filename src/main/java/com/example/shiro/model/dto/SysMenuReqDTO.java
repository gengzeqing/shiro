package com.example.shiro.model.dto;

import com.example.shiro.model.base.PageReq;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yaokui
 * @Date: 2019/6/11
 */
@Getter
@Setter
public class SysMenuReqDTO extends PageReq {

    private static final long serialVersionUID = 517488146727683246L;

    /**
     * 菜单名称
     */
    private String menuName;
}
