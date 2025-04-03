package com.baolong.init.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户禁用状态枚举
 *
 * @author Silas Yan 2025-04-03:20:07
 */
@Getter
public enum UserDisabledEnum {

	NORMAL(0, "正常"),
	DISABLED(1, "禁用");

	private final Integer key;

	private final String label;

	UserDisabledEnum(Integer key, String label) {
		this.key = key;
		this.label = label;
	}

	/**
	 * 根据 KEY 获取枚举
	 *
	 * @param key 状态键值
	 * @return 枚举对象，未找到时返回 null
	 */
	public static UserDisabledEnum of(Integer key) {
		if (ObjUtil.isEmpty(key)) return null;
		return ArrayUtil.firstMatch(e -> e.getKey().equals(key), values());
	}

	/**
	 * 获取所有 KEY 列表
	 *
	 * @return KEY 集合
	 */
	public static List<Integer> keys() {
		return Arrays.stream(values())
				.map(UserDisabledEnum::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 判断是否为禁用状态
	 *
	 * @param key 状态键值
	 * @return 是否禁用
	 */
	public static boolean isDisabled(Integer key) {
		return DISABLED.getKey().equals(key);
	}
}
