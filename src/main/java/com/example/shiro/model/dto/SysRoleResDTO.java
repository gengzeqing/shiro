package com.example.shiro.model.dto;

import com.example.shiro.model.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Generator
 * @since 2019-06-04
 */
@Getter
@Setter
public class SysRoleResDTO extends BaseDTO {

    private static final long serialVersionUID = 2151974602892962327L;

    /**
     * 角色id
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

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 创建时间
     */
    private String createTime;

}
