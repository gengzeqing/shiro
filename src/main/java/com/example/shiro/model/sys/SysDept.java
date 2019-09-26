package com.example.shiro.model.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.example.shiro.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDept extends BaseModel<SysDept> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 父部门id
     */
    private Long parentId;
    /**
     * 部门简称
     */
    @TableField("simple_name")
    private String simpleName;
    /**
     * 部门全称
     */
    @TableField("full_name")
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
     * 删除(0：未删除  1：已删除）
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
