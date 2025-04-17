package com.silas.init.common.response;

import lombok.Getter;

/**
 * 响应码枚举
 *
 * @author Silas Yan 2025-04-02:23:58
 */
@Getter
public enum RespCode {

	/**
	 * 成功
	 */
	SUCCESS(0, "SUCCESS"),
	/**
	 * 失败
	 */
	FAILED(400, "FAILED"),
	/**
	 * 系统异常
	 */
	SYSTEM_ERROR(500, "系统异常"),

	/**
	 * 参数错误
	 */
	ERROR_PARAMETER(400, "参数错误"),
	/**
	 * 数据错误
	 */
	ERROR_DATA(400, "数据错误"),
	/**
	 * 禁止访问
	 */
	ERROR_FORBIDDEN(402, "禁止访问"),
	/**
	 * 请求方式错误
	 */
	ERROR_REQUEST_METHOD(405, "请求方式错误"),
	/**
	 * 操作错误
	 */
	ERROR_OPERATION(500, "操作错误"),

	/**
	 * 未登录
	 */
	NOT_LOGIN(401, "未登录"),
	/**
	 * 无权限
	 */
	NOT_AUTH(403, "无权限"),
	;

	/**
	 * 状态码
	 */
	private final int code;

	/**
	 * 描述信息
	 */
	private final String message;

	RespCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
