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
public class SysOperationLogResDTO extends BaseDTO {

    private static final long serialVersionUID = -5666972933349066484L;

    /**
     * 主键
     */
    private Long opLogId;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 日志名称
     */
    private String logName;
    /**
     * 管理员id
     */
    private Long mUserId;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 备注信息
     */
    private String message;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 创建时间
     */
    private String createTime;

}
