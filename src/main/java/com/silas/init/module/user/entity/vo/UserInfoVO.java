package com.silas.init.module.user.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息VO
 *
 * @author Silas Yan
 */
@Data
public class UserInfoVO implements Serializable {

	@Schema(description = "用户ID")
	private Long id;

	@Schema(description = "账号")
	private String userAccount;

	@Schema(description = "用户邮箱")
	private String userEmail;

	@Schema(description = "用户手机号")
	private String userPhone;

	@Schema(description = "用户昵称")
	private String userName;

	@Schema(description = "用户头像")
	private String userAvatar;

	@Schema(description = "用户简介")
	private String userProfile;

	@Schema(description = "用户角色")
	private String userRole;

	@Serial
	private static final long serialVersionUID = 1L;
}
