package com.silas.init.module.main.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 验证码响应类
 *
 * @author Silas Yan 2025-04-06:00:48
 */
@Data
public class CaptchaVO implements Serializable {

	@Schema(description = "图形验证码KEY", requiredMode = Schema.RequiredMode.REQUIRED)
	private String captchaKey;

	@Schema(description = "图形验证码图片, Base64 格式", requiredMode = Schema.RequiredMode.REQUIRED)
	private String captchaImage;

	@Serial
	private static final long serialVersionUID = 1L;
}
