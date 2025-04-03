package com.baolong.init.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 删除状态枚举
 *
 * @author Silas Yan 2025-04-03:20:04
 */
@Getter
public enum DeleteStatusEnum {

	NORMAL(0, "正常"),
	DELETE(1, "删除");

	private final Integer key;

	private final String label;

	DeleteStatusEnum(Integer key, String label) {
		this.key = key;
		this.label = label;
	}

	/**
	 * 根据 KEY 获取枚举
	 *
	 * @param key 状态键值
	 * @return 枚举对象，未找到时返回 null
	 */
	public static DeleteStatusEnum of(Integer key) {
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
				.map(DeleteStatusEnum::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 判断是否删除
	 *
	 * @param key 状态键值
	 * @return 是否删除
	 */
	public static boolean isDelete(Integer key) {
		return DELETE.getKey().equals(key);
	}
}
