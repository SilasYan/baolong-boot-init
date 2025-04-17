package com.silas.init.module.user.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新密码请求
 *
 * @author Silas Yan 2025-04-09:19:08
 */
@Data
public class UserUpdatePasswordRequest implements Serializable {

	@Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long id;

	@Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
	private String oldPassword;

	@Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
	private String newPassword;

	@Schema(description = "确认密码", requiredMode = Schema.RequiredMode.REQUIRED)
	private String confirmPassword;

	@Serial
	private static final long serialVersionUID = 1L;
}
