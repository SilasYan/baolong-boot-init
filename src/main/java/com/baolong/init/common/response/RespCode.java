package com.baolong.init.common.response;

import lombok.Getter;

/**
 * 响应码枚举
 *
 * @author Silas Yan 2025-04-02:23:58
 */
@Getter
public enum RespCode {

	SUCCESS(0, "SUCCESS"),
	FAILED(444, "FAILED"),
	SYSTEM_ERROR(500, "系统异常"),
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
