package com.silas.init.module.user.entity.enums;

import com.silas.init.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 用户禁用状态枚举
 *
 * @author Silas Yan 2025-04-03:20:07
 */
@Getter
public enum UserDisabledEnum implements BaseEnum<Integer> {

	/**
	 * 正常
	 */
	NORMAL(0, "正常"),
	/**
	 * 禁用
	 */
	DISABLED(1, "禁用");

	private final Integer key;
	private final String desc;

	UserDisabledEnum(Integer key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	/**
	 * 判断是否为禁用状态
	 *
	 * @param key 状态键值
	 * @return 是否禁用
	 */
	public static boolean isDisabled(Integer key) {
		return DISABLED.is(key);
	}
}
