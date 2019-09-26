package com.example.shiro.common.enums;

/**
 * @Description 所有业务异常的枚举
 * @Author: yaokui
 * @Date: 2019/6/3
 */

public enum BizExceptionEnum {

	/**
	 * 系统错误 1000500
	 */
	SERVER_ERROR("1000500", "服务器异常"),
	REQUEST_NULL("1000501", "请求有错误"),
	SESSION_TIMEOUT("1000502", "登录已失效，请重新登录"),

	/**
	 * 请求数据 1001100
	 */
	DB_RESOURCE_NULL("1001100","没有找到该资源"),
	NO_PERMISSION("1001102", "权限异常"),
	REQUEST_INVALIDATE("1001103","请求参数不正确"),
	INVALID_CAPTCHA("1001104","验证码不正确"),
	TOKEN_INVALID("1001209","TOKEN失效"),

	/**
	 * 账户问题 1001200
	 */
	USER_NOT_LOGIN("1001200","登录失效，请重新登录"),
	USER_NOT_EXISTED("1001201", "该账号不存在"),
	ACCOUNT_FROZEN("1001202", "您的账号已被锁定，请联系管理员"),
	ERROR_ACCOUNT_PWD("1001203", "账号密码错误"),
	OLD_PWD_NOT_RIGHT("1001204", "原密码不正确"),
	TWO_PWD_NOT_MATCH("1001205", "两次输入密码不一致"),
	NEW_PSW_EQ_OLD_PSW("1001206", "与原始密码一致，不能修改密码"),
	USER_ALREADY_REG("1001207","该账号已添加"),
	UUID_BLANK("1001208","UUID不能为空"),
	ACCOUNT_PWD_ERR_FROZEN("1001209", "您的账号已被锁定，将于次日00:00自动解锁，或联系管理员"),

	/**
	 * 权限和数据问题 1001300
	 */
	CANT_DELETE_ADMIN("1001301","您没有权限删除超级管理员账户"),
	CANT_FREEZE_ADMIN("1001302","您没有权限冻结超级管理员账户"),
	CANT_CHANGE_ADMIN("1001303","您没有权限修改超级管理员账户"),
	DEPT_NOT_EXISTED("1001304", "该部门不存在"),
	MENU_NOT_EXISTED("1001305", "该菜单不存在"),
	EXISTED_THE_MENU("1001306","菜单权限标识重复，不能添加"),
	DELETE_MENU_HAS_CHILD("1001307","有关联子菜单，不能删除"),
	DELETE_MENU_HAS_USED("1001308","有角色关联该菜单权限，不能删除"),
    ROLE_NOT_EXISTED("1001309", "该角色不存在"),
	DELETE_ROLE_HAS_USED("1001308","有用户账号关联该角色，不能删除"),
	CANT_CHANGE_ADMIN_ROLE("1001309","您没有权限修改超级管理员角色"),
	CANT_DELETE_ADMIN_ROLE("1001310","您没有权限删除超级管理员角色"),
	CANT_ALLOT_ADMIN_ROLE("1001311","您不能变更超级管理员角色的权限设置"),
	CANT_RESET_PWD_ADMIN("1001312","您没有权限重置超级管理员账户密码"),
	CANT_UNFREEZE_ADMIN("1001313","您没有权限解冻超级管理员账户"),
	CANT_ALLOT_ADMIN("1001314","您不能变更超级管理员账户的角色分配"),
	CANT_CHANGE_SELF_ACCOUNT("1001315","您没有权限修改自己的账户"),
	CANT_FREEZE_SELF_ACCOUNT("1001316","您没有权限冻结自己的账户"),
	CANT_UNFREEZE_SELF_ACCOUNT("1001317","您没有权限解冻自己的账户"),
	CANT_DELETE_SELF_ACCOUNT("1001318","您没有权限删除自己的账户"),
	CANT_ALLOT_SELF_ACCOUNT("1001319","您没有权限变更自己账户的角色分配"),

	;

	BizExceptionEnum(String code, String message) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
	}
	
	BizExceptionEnum(String code, String message, String urlPath) {
		this.friendlyCode = code;
		this.friendlyMsg = message;
		this.urlPath = urlPath;
	}

	private String friendlyCode;

	private String friendlyMsg;
	
	private String urlPath;

	public String getFriendlyCode() {
		return friendlyCode;
	}

	public void setFriendlyCode(String friendlyCode) {
		this.friendlyCode = friendlyCode;
	}

	public String getFriendlyMsg() {
		return friendlyMsg;
	}

	public void setFriendlyMsg(String friendlyMsg) {
		this.friendlyMsg = friendlyMsg;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
}
