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
public class SysRoleForm implements Serializable {

    private static final long serialVersionUID = 5434496594114414478L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色别名
     */
    private String roleAlias;
    /**
     * 排序优先级
     */
    private Integer priority;
    /**
     * 父角色id
     */
    private Long parentId;
    /**
     * 部门id
     */
    private Long deptId;
}
