package com.study.demo.constant;

public enum ApplicationEnum {
	SUCCESS(0, "操作成功"),
	FAIL(1, "系统异常，请稍后重试!"),
	PATH_ERROR(1, "访问地址未找到"),
	LOGIN_SUCCESS(0, "登录成功"),
	LOGIN_FAIL(1, "账号或密码错误"),
	UNAUTHORIZED(1, "您没有当前操作的权限！"),
	UNLOGIN(2, "未登录"),
	LOGOUT(2, "退出成功"),
	FILE_NOT_EXISTS(1000, "文件不存在"),
	FILE_UPLOAD_ERROR(1001, "上传失败"),
	PARAM_ERROR(1002, "参数异常"),
	FILE_NOT_EMPTY(1003, "上传文件不能为空文件"),
	TEMPLATE_NOT_FOUND(1004, "模板未找到");

	private int code;

	private String message;

	private ApplicationEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
