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
 * 角色和菜单关联表
 * </p>
 *
 * @author Generator
 * @since 2019-06-04
 */
@Getter
@Setter
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseModel<SysRoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 菜单id
     */
    @TableField("menu_id")
    private Long menuId;
    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;
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
