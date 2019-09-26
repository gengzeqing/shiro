package com.example.shiro.model.builder;

import com.example.shiro.common.enums.DeletedStatus;
import com.example.shiro.common.enums.EnableStatus;
import com.example.shiro.common.enums.MenuType;
import com.example.shiro.common.enums.YesNoStatus;
import com.example.shiro.model.dto.SysMenuResDTO;
import com.example.shiro.model.form.SysMenuForm;
import com.example.shiro.model.sys.SysMenu;
import com.example.shiro.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: yaokui
 * @Date: 2019/6/10
 */
public class SysMenuBuilder {

    public static SysMenuResDTO modelToDto(SysMenu copy) {
        SysMenuResDTO sysMenuResDTO = new SysMenuResDTO();
        sysMenuResDTO.setMenuId(copy.getId());
        sysMenuResDTO.setMenuCode(copy.getCode());
        sysMenuResDTO.setParentId(copy.getParentId());
        sysMenuResDTO.setMenuName(copy.getName());
        sysMenuResDTO.setMenuUrl(copy.getUrl());
        sysMenuResDTO.setMenuType(copy.getType());
        sysMenuResDTO.setMenuTypeMsg(MenuType.valueOf(copy.getType()));
        sysMenuResDTO.setPriority(copy.getPriority());
        sysMenuResDTO.setMenuIcon(copy.getIcon());
        sysMenuResDTO.setIsOpen(copy.getIsOpen());
        sysMenuResDTO.setLevels(copy.getLevels());
        sysMenuResDTO.setStatus(copy.getStatus());
        sysMenuResDTO.setStatusMsg(EnableStatus.valueOf(copy.getStatus()));
        sysMenuResDTO.setCreateTime(DateUtils.format(copy.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
        sysMenuResDTO.setTips(copy.getTips());

        return sysMenuResDTO;
    }

    /**
     * 使用递归方法建树
     * @param menuList
     * @return
     */
    public static List<SysMenuResDTO> buildTree(List<SysMenuResDTO> menuList) {
        List<SysMenuResDTO> treeList = new ArrayList<>();
        for (SysMenuResDTO treeNode : menuList) {
            if (treeNode.getParentId() == 0) {
                treeList.add(findChildren(treeNode,menuList));
            }
        }
        return treeList;
    }

    /**
     * 递归查找子节点
     * @param menuList
     * @return
     */
    public static SysMenuResDTO findChildren(SysMenuResDTO treeNode, List<SysMenuResDTO> menuList) {
        for (SysMenuResDTO it : menuList) {
            if(treeNode.getMenuId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,menuList));
            }
        }
        return treeNode;
    }

    public static SysMenu createModel(SysMenuForm copy) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setCode(copy.getMenuCode());
        sysMenu.setParentId(copy.getParentId());
        sysMenu.setName(copy.getMenuName());
        sysMenu.setIcon(copy.getMenuIcon());
        sysMenu.setUrl(copy.getMenuUrl());
        sysMenu.setPriority(copy.getPriority());
        sysMenu.setLevels(copy.getLevels());
        sysMenu.setType(copy.getMenuType());
        sysMenu.setTips(copy.getTips());
        sysMenu.setStatus(EnableStatus.ENABLED.getCode());
        sysMenu.setIsOpen(YesNoStatus.NO.getCode());
        sysMenu.setDeleted(DeletedStatus.UN_DELETED.getCode());
        sysMenu.setCreateTime(new Date());
        sysMenu.setModifyTime(new Date());

        return sysMenu;
    }

    public static SysMenu updateModel(SysMenuForm copy, SysMenu sysMenu) {
        sysMenu.setId(copy.getMenuId());
        sysMenu.setCode(copy.getMenuCode());
        sysMenu.setParentId(copy.getParentId());
        sysMenu.setName(copy.getMenuName());
        sysMenu.setIcon(copy.getMenuIcon());
        sysMenu.setUrl(copy.getMenuUrl());
        sysMenu.setPriority(copy.getPriority());
        sysMenu.setLevels(copy.getLevels());
        sysMenu.setType(copy.getMenuType());
        sysMenu.setTips(copy.getTips());
        sysMenu.setModifyTime(new Date());

        return sysMenu;
    }
}
