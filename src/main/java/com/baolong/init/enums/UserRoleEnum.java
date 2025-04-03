package com.baolong.init.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *
 * @author Silas Yan 2025-04-03:20:02
 */
@Getter
public enum UserRoleEnum {

	USER("USER", "用户"),
	ADMIN("ADMIN", "管理员");

	private final String key;

	private final String label;

	UserRoleEnum(String key, String label) {
		this.key = key;
		this.label = label;
	}

	/**
	 * 根据 KEY 获取枚举
	 *
	 * @param key 状态键值
	 * @return 枚举对象，未找到时返回 null
	 */
	public static UserRoleEnum of(String key) {
		if (ObjUtil.isEmpty(key)) return null;
		return ArrayUtil.firstMatch(e -> e.getKey().equals(key), values());
	}

	/**
	 * 获取所有 KEY 列表
	 *
	 * @return KEY 集合
	 */
	public static List<String> keys() {
		return Arrays.stream(values())
				.map(UserRoleEnum::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 判断是否为管理员
	 *
	 * @param key 状态键值
	 * @return 是否管理员
	 */
	public static boolean isAdmin(String key) {
		return ADMIN.getKey().equals(key);
	}
}
