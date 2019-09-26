package com.example.shiro.model.dto;

import com.example.shiro.model.base.PageReq;
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
public class SysOperationLogReqDTO extends PageReq {

    private static final long serialVersionUID = 6738208017409420688L;

    /**
     * 日志类型
     */
    private String logType;
    /**
     * 日志名称
     */
    private String logName;

}
