package com.silas.init.constants;

/**
 * KEY常量
 *
 * @author Silas Yan 2025-04-06:01:04
 */
public interface KeyConstant {

	/**
	 * 图形验证码 KEY 前缀
	 */
	String PREFIX_CAPTCHA_CODE = "CAPTCHA_CODE:%s";

	/**
	 * 邮箱验证码 KEY 前缀
	 */
	String PREFIX_EMAIL_CODE = "EMAIL_CODE:%s";

	/**
	 * 用户信息 KEY 前缀
	 */
	String PREFIX_USER_INFO = "USER_INFO:%s";

	/**
	 * 用户信息过期时间
	 */
	long USER_INFO_TIME = 1;

	/**
	 * 用户签到集合
	 */
	String USER_SIGN_IN_SET = "USER_SIGN_IN";
}
