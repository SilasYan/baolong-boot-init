package com.silas.init.auth;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.silas.init.common.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置类
 *
 * @author Silas Yan 2025-04-05:21:04
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

	/**
	 * Sa-Token 整合 jwt (Simple 简单模式)
	 *
	 * @return StpLogic
	 */
	@Bean
	public StpLogic getStpLogicJwt() {
		return new StpLogicJwtForSimple();
	}

	/**
	 * 注册拦截器
	 *
	 * @param registry InterceptorRegistry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SaInterceptor(handler -> {
							HttpServletRequest request = ServletUtil.getRequest();
							if (request != null) {
								// 排除 OPTIONS 请求
								if ("OPTIONS".equals(ServletUtil.getRequest().getMethod())) {
									return;
								}
								System.out.println("--------------地址: " + request.getRequestURI());
								// Enumeration<String> headerNames = request.getHeaderNames();
								// while (headerNames.hasMoreElements()) {
								// 	String headerName = headerNames.nextElement();
								// 	System.out.println(headerName + ": " + request.getHeader(headerName));
								// }
							}
							SaRouter.match("/**")
									// 排除掉的 path 列表，可以写多个
									.notMatch("/picture/home/list", "/category/home/list", "/picture/detail")
									.check(r -> StpUtil.checkLogin());
						})
				)
				.addPathPatterns("/**")
				.excludePathPatterns("/error", "/favicon.ico", "/doc.html", "/v3/api-docs/**", "/webjars/**"
						, "/health"
						, "/send/email/code"
						, "/captcha"
						, "/user/register"
						, "/user/login"
				);
	}
}
