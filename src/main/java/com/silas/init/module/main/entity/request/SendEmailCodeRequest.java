package com.silas.init.module.main.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 发送邮箱验证码请求
 *
 * @author Silas Yan 2025-04-06:00:39
 */
@Data
public class SendEmailCodeRequest implements Serializable {

	/**
	 * 用户邮箱
	 */
	@Schema(description = "用户邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userEmail;

	@Serial
	private static final long serialVersionUID = 1L;
}
