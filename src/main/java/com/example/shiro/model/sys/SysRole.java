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
 * 角色表
 * </p>
 *
 * @author Generator
 * @since 2019-06-04
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRole extends BaseModel<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色别名
     */
    private String alias;
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
    @TableField("dept_id")
    private Long deptId;
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
