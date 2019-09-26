package com.example.shiro.model.dto;

import com.example.shiro.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * [菜单DTO]
 *
 * @author yaokui
 * @since 2019-06-04
 */
@Getter
@Setter
public class SysMenuResDTO extends BaseDTO {

    private static final long serialVersionUID = 1462436395174308851L;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 菜单标识
     */
    private String menuCode;

    /**
     * 菜单父id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单类型
     */
    private Integer menuType;

    /**
     * 菜单类型信息
     */
    private String menuTypeMsg;

    /**
     * 菜单排序
     */
    private Integer priority;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 是否打开（1:打开   0:不打开）
     */
    private Integer isOpen;

    /**
     * 菜单层级
     */
    private Integer levels;

    /**
     * 菜单状态
     */
    private Integer status;

    /**
     * 菜单状态信息
     */
    private String statusMsg;

    /**
     * 备注（前端使用）
     */
    private String tips;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 子菜单集合
     */
    private List<SysMenuResDTO> children;

}
