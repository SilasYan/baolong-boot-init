package com.silas.init.module.user.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求
 *
 * @author Silas Yan 2025-04-06:02:30
 */
@Data
public class UserLoginRequest implements Serializable {

	@Schema(description = "用户账号/用户邮箱")
	private String account;

	@Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userPassword;

	@Schema(description = "图形验证码KEY", requiredMode = Schema.RequiredMode.REQUIRED)
	private String captchaKey;

	@Schema(description = "图形验证码值", requiredMode = Schema.RequiredMode.REQUIRED)
	private String captchaValue;

	@Serial
	private static final long serialVersionUID = 1L;
}
