package com.silas.init.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 *
 * @author Silas Yan 2025-04-05:21:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

	/**
	 * 是否登录
	 */
	boolean isLogin() default true;

	/**
	 * 角色标识
	 */
	String role() default "";
}
