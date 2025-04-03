package com.baolong.init.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户新增请求
 *
 * @author Silas Yan 2025-04-03:20:27
 */
@Schema(name = "UserAddRequest", description = "用户新增请求")
@Data
public class UserAddRequest implements Serializable {
	/**
	 * 账号
	 */
	@Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userAccount;

	/**
	 * 用户邮箱
	 */
	@Schema(description = "用户邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userEmail;

	/**
	 * 用户手机号
	 */
	@Schema(description = "用户手机号")
	private String userPhone;

	/**
	 * 用户昵称
	 */
	@Schema(description = "用户昵称")
	private String userName;

	@Serial
	private static final long serialVersionUID = 1L;
}
