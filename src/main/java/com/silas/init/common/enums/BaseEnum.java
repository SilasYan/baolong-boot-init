package com.silas.init.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础枚举类
 *
 * @author Silas Yan 2025-04-05:20:27
 */
public interface BaseEnum<T> {

	/**
	 * 键
	 *
	 * @return 泛型对应的KEY
	 */
	T getKey();

	/**
	 * 描述
	 *
	 * @return 当前KEY的描述
	 */
	String getDesc();

	/**
	 * 根据枚举类型和KEY获取枚举
	 *
	 * @param enumClass 枚举类型
	 * @param key       KEY
	 * @param <E>       枚举泛型
	 * @param <T>       KEY泛型
	 * @return 枚举
	 */
	static <E extends Enum<E> & BaseEnum<T>, T> E of(Class<E> enumClass, T key) {
		if (key == null) {
			return null;
		}
		for (E enumItem : enumClass.getEnumConstants()) {
			if (key.equals(enumItem.getKey())) {
				return enumItem;
			}
		}
		return null;
	}

	/**
	 * 根据KEY获取描述
	 *
	 * @param enumClass 枚举类型
	 * @param key       KEY
	 * @param <E>       枚举泛型
	 * @param <T>       KEY泛型
	 * @return 描述
	 */
	static <E extends Enum<E> & BaseEnum<T>, T> String ofDesc(Class<E> enumClass, T key) {
		E enumItem = of(enumClass, key);
		return enumItem != null ? enumItem.getDesc() : null;
	}

	/**
	 * 获取所有枚举的KEY
	 *
	 * @param enumClass 枚举类型
	 * @param <E>       枚举泛型
	 * @param <T>       KEY泛型
	 * @return KEY集合
	 */
	static <E extends Enum<E> & BaseEnum<T>, T> List<T> keys(Class<E> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants())
				.map(BaseEnum::getKey)
				.collect(Collectors.toList());
	}

	/**
	 * 判断KEY是否匹配
	 *
	 * @param key 键
	 * @return 是否匹配
	 */
	default boolean is(T key) {
		return this.getKey().equals(key);
	}
}
