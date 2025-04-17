package com.silas.init.module.user.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户禁用请求
 *
 * @author Silas Yan 2025-04-09:19:08
 */
@Data
public class UserBanRequest implements Serializable {

	@Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private Long id;

	@Schema(description = "状态")
	private Integer status;

	@Serial
	private static final long serialVersionUID = 1L;
}
