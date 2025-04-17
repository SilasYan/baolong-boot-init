package com.silas.init.constants;

/**
 * 基本常量
 *
 * @author Silas Yan 2025-04-06:01:57
 */
public interface BaseConstant {

	int ZERO = 0;
	int ONE = 1;
	int TWO = 2;
	int THREE = 3;
	int FOUR = 4;
	int FIVE = 5;
	int SIX = 6;
	int SEVEN = 7;
	int EIGHT = 8;
	int NINE = 9;
	int TEN = 10;
	int ELEVEN = 11;
	int TWELVE = 12;
	int TWENTY = 20;
	int ONT_HUNDRED_TWENTY = 120;
	int TWO_HUNDRED_FIFTY = 250;
	int FIVE_HUNDRED = 500;

	String EMPTY = "";

	/**
	 * 正则: 是否以数字开头
	 */
	String REGULAR_PREFIX_NUMBER = "^\\d.*";

	/**
	 * 账户前缀
	 */
	String PREFIX_ACCESS_KEY = "ak-%s%s";

	/**
	 * 密钥前缀
	 */
	String PREFIX_SECRET_KEY = "sk-%s%s";

	/**
	 * 50 积分
	 */
	int POINTS_50 = 50;

	/**
	 * 100 积分
	 */
	int POINTS_100 = 100;

	/**
	 * 角色: 管理员
	 */
	String ROLE_ADMIN = "ADMIN";

	/**
	 * 角色: 用户
	 */
	String ROLE_USER = "USER";
}
