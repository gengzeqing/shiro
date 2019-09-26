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
 * 管理员表
 * </p>
 *
 * @author Generator
 * @since 2019-06-04
 */
@Getter
@Setter
@TableName("sys_admin")
public class SysAdmin extends BaseModel<SysAdmin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    @TableField("cn_name")
    private String cnName;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 0 脱敏访问 1正常访问
     */
    @TableField("desensitization_type")
    private Integer desensitizationType;
    /**
     * 状态(0：冻结  1：启用）
     */
    private Integer status;
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
