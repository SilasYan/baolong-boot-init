package com.silas.init.module.user.entity.enums;

import com.silas.init.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @author Silas Yan 2025-04-03:20:02
 */
@Getter
public enum UserRoleEnum implements BaseEnum<String> {

	/**
	 * 用户
	 */
	USER("USER", "用户"),
	/**
	 * 管理员
	 */
	ADMIN("ADMIN", "管理员");

	private final String key;
	private final String desc;

	UserRoleEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	/**
	 * 判断是否为管理员
	 *
	 * @param key 状态键值
	 * @return 是否管理员
	 */
	public static boolean isAdmin(String key) {
		return ADMIN.is(key);
	}
}
