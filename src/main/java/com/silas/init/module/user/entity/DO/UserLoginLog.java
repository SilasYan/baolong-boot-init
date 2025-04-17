package com.silas.init.module.user.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录日志表
 *
 * @author Silas Yan
 * @TableName user_login_log
 */
@TableName(value = "user_login_log")
@Data
@Accessors(chain = true)
public class UserLoginLog implements Serializable {
	/**
	 * 主键 ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 用户ID
	 */
	@TableField(value = "user_id")
	private Long userId;

	/**
	 * 登录IP
	 */
	@TableField(value = "login_ip")
	private String loginIp;

	/**
	 * 登录时间
	 */
	@TableField(value = "login_time")
	private Date loginTime;

	/**
	 * 登录设备信息
	 */
	@TableField(value = "user_agent")
	private String userAgent;

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
