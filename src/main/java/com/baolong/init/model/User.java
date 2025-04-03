package com.baolong.init.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "user")
@Data
public class User implements Serializable {
	/**
	 * 主键 ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 账号
	 */
	@TableField(value = "user_account")
	private String userAccount;

	/**
	 * 密码
	 */
	@TableField(value = "user_password")
	private String userPassword;

	/**
	 * 用户邮箱
	 */
	@TableField(value = "user_email")
	private String userEmail;

	/**
	 * 用户手机号
	 */
	@TableField(value = "user_phone")
	private String userPhone;

	/**
	 * 用户昵称
	 */
	@TableField(value = "user_name")
	private String userName;

	/**
	 * 用户头像
	 */
	@TableField(value = "user_avatar")
	private String userAvatar;

	/**
	 * 用户简介
	 */
	@TableField(value = "user_profile")
	private String userProfile;

	/**
	 * 用户角色（USER-普通用户, ADMIN-管理员）
	 */
	@TableField(value = "user_role")
	private String userRole;

	/**
	 * 分享码
	 */
	@TableField(value = "share_code")
	private String shareCode;

	/**
	 * 是否禁用（0-正常, 1-禁用）
	 */
	@TableField(value = "is_disabled")
	private Integer isDisabled;

	/**
	 * 是否删除（0-正常, 1-删除）
	 */
	@TableField(value = "is_delete")
	private Integer isDelete;

	/**
	 * 编辑时间
	 */
	@TableField(value = "edit_time", fill = FieldFill.UPDATE)
	private Date editTime;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time")
	private Date updateTime;

	@Serial
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}
