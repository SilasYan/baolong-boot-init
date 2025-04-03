package com.baolong.init.exception;

import com.baolong.init.common.response.RespCode;
import lombok.Getter;

/**
 * 业务异常类
 *
 * @author Silas Yan 2025-04-03:00:11
 */
@Getter
public class BusinessException extends RuntimeException {

	private final int code;

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(RespCode respCode) {
		super(respCode.getMessage());
		this.code = respCode.getCode();
	}

	public BusinessException(RespCode respCode, String message) {
		super(message);
		this.code = respCode.getCode();
	}
}
