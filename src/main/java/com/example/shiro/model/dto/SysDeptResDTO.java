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
public class SysDeptResDTO extends BaseDTO {

    private static final long serialVersionUID = 6858732944450622428L;

    /**
     * 主键id
     */
    private Long deptId;
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 部门简称
     */
    private String simpleName;
    /**
     * 部门全称
     */
    private String fullName;
    /**
     * 排序
     */
    private Integer priority;
    /**
     * 提示
     */
    private String tips;
    /**
     * 创建时间
     */
    private String createTime;
}
