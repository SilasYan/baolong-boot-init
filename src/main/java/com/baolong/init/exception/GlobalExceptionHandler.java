package com.baolong.init.exception;

import com.baolong.init.common.response.BaseResponse;
import com.baolong.init.common.response.RUtils;
import com.baolong.init.common.response.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Silas Yan 2025-04-03:00:13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 业务异常处理器
	 *
	 * @param e 业务异常
	 */
	@ExceptionHandler(BusinessException.class)
	public BaseResponse<?> businessExceptionHandler(BusinessException e) {
		log.error("[业务异常]{}", e.getMessage());
		return RUtils.failed(e.getCode(), e.getMessage());
	}

	/**
	 * 全局异常处理器
	 *
	 * @param e 异常
	 */
	@ExceptionHandler({RuntimeException.class, Exception.class})
	public BaseResponse<?> otherExceptionHandler(Exception e) {
		log.error("[全局异常]", e);
		return RUtils.failed(RespCode.SYSTEM_ERROR);
	}
}
