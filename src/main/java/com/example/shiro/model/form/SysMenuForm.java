package com.example.shiro.model.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: yaokui
 * @Date: 2019/6/11
 */
@Getter
@Setter
public class SysMenuForm implements Serializable {

    private static final long serialVersionUID = 4648151418779461302L;

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 菜单标识
     */
    private String menuCode;
    /**
     * 菜单父ID
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * url地址
     */
    private String menuUrl;
    /**
     * 菜单排序号
     */
    private Integer priority;
    /**
     * 菜单层级
     */
    private Integer levels;
    /**
     * 类型（0：其他  1：菜单  2：按钮）
     */
    private Integer menuType;
    /**
     * 备注
     */
    private String tips;
}
