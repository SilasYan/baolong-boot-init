package com.silas.init.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.silas.init.annotation.AuthCheck;
import com.silas.init.common.enums.BaseEnum;
import com.silas.init.common.exception.BusinessException;
import com.silas.init.common.response.RespCode;
import com.silas.init.constants.TextConstant;
import com.silas.init.module.user.entity.DO.User;
import com.silas.init.module.user.entity.enums.UserDisabledEnum;
import com.silas.init.module.user.entity.enums.UserRoleEnum;
import com.silas.init.module.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * 权限校验切面
 *
 * @author Silas Yan 2025-04-05:21:15
 */
@Slf4j
public class AuthCheckAspect {

	@Resource
	private UserService userService;

	@Around("@annotation(authCheck)")
	public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
		// 需要登录，校验登录
		if (authCheck.isLogin()) {
			if (!StpUtil.isLogin()) {
				throw new BusinessException(RespCode.NOT_LOGIN);
			}
		}
		// 校验是否指定角色
		String role = authCheck.role();
		UserRoleEnum userRoleEnum = BaseEnum.of(UserRoleEnum.class, role);
		if (UserRoleEnum.isAdmin(role)) {
			return joinPoint.proceed();
		}
		User user = userService.getById(StpUtil.getLoginIdAsLong());
		if (UserDisabledEnum.isDisabled(user.getIsDisabled())) {
			log.error("[权限校验] 校验失败! {}", TextConstant.INFO_USER_DISABLED);
			throw new BusinessException(RespCode.ERROR_FORBIDDEN, TextConstant.INFO_USER_DISABLED);
		}
		if (!userRoleEnum.getKey().equals(user.getUserRole())) {
			log.error("[权限校验] 校验失败! {}", RespCode.NOT_AUTH.getMessage());
			throw new BusinessException(RespCode.NOT_AUTH);
		}
		return joinPoint.proceed();
	}
}
