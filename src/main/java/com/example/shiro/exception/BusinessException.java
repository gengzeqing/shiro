package com.example.shiro.exception;


import com.example.shiro.common.enums.BizExceptionEnum;
import com.example.shiro.common.enums.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 业务异常的封装
 * @Author: yaokui
 * @Date: 2019/6/3
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	//友好提示的code码
	protected String friendlyCode;

	//友好提示
	protected String friendlyMsg;

	//业务异常跳转的页面
	protected String urlPath;

	public BusinessException(String friendlyMsg) {
		this.setValues(RetCodeEnum.FAILED.getCode(), friendlyMsg, "");
	}

	public BusinessException(String friendlyCode, String friendlyMsg, String urlPath) {
		this.setValues(friendlyCode, friendlyMsg, urlPath);
	}

	public BusinessException(BizExceptionEnum bizExceptionEnum) {
		this.setValues(bizExceptionEnum.getFriendlyCode(), bizExceptionEnum.getFriendlyMsg(), bizExceptionEnum.getUrlPath());
	}

	public BusinessException(BizExceptionEnum bizExceptionEnum, String friendlyMsg) {
		this.setValues(bizExceptionEnum.getFriendlyCode(), friendlyMsg, bizExceptionEnum.getUrlPath());
	}

	private void setValues(String friendlyCode, String friendlyMsg, String urlPath) {
		this.friendlyCode = friendlyCode;
		this.friendlyMsg = friendlyMsg;
		this.urlPath = urlPath;
	}

}
