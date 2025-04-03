package com.baolong.init.model.request;

import com.baolong.init.common.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户查询请求
 *
 * @author Silas Yan 2025-04-03:20:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

	/**
	 * 用户 ID
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String userAccount;

	/**
	 * 用户邮箱
	 */
	private String userEmail;

	/**
	 * 用户手机号
	 */
	private String userPhone;

	/**
	 * 用户昵称
	 */
	private String userName;

	/**
	 * 用户简介
	 */
	private String userProfile;

	/**
	 * 用户角色（USER-普通用户, ADMIN-管理员）
	 */
	private String userRole;

	/**
	 * 分享码
	 */
	private String shareCode;

	/**
	 * 是否禁用（0-正常, 1-禁用）
	 */
	private Integer isDisabled;

	/**
	 * 编辑时间[开始时间]
	 */
	private String startEditTime;

	/**
	 * 编辑时间[结束时间]
	 */
	private String endEditTime;

	@Serial
	private static final long serialVersionUID = 1L;
}
