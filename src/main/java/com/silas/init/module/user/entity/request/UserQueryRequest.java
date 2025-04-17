package com.silas.init.module.user.entity.request;

import com.silas.init.common.page.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author Silas Yan 2025-04-12:00:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

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

	@Schema(description = "用户简介")
	private String userProfile;

	@Schema(description = "用户角色（USER-普通用户, ADMIN-管理员）")
	private String userRole;

	@Schema(description = "是否禁用（0-正常, 1-禁用）")
	private Integer isDisabled;

	@Schema(description = "编辑时间[开始时间]")
	private String startEditTime;

	@Schema(description = "编辑时间[结束时间]")
	private String endEditTime;

	@Serial
	private static final long serialVersionUID = 1L;
}
