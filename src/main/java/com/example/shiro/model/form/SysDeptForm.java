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
public class SysDeptForm implements Serializable {
    private static final long serialVersionUID = 3865150216861049997L;

    /**
     * 部门ID
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
}
