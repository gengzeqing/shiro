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
 * 菜单表
 * </p>
 *
 * @author Generator
 * @since 2019-06-03
 */
@Getter
@Setter
@TableName("sys_menu")
public class SysMenu extends BaseModel<SysMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 菜单标识
     */
    private String code;
    /**
     * 菜单父ID
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * url地址
     */
    private String url;
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
    private Integer type;
    /**
     * 备注
     */
    private String tips;
    /**
     * 菜单状态（1：启用  2：冻结  3：删除）
     */
    private Integer status;
    /**
     * 是否打开（1:打开   0:不打开）
     */
    private Integer isOpen;
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
