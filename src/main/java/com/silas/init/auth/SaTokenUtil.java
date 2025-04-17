package com.silas.init.auth;

import cn.dev33.satoken.stp.StpUtil;
import com.silas.init.common.exception.ThrowUtil;
import com.silas.init.common.response.RespCode;

/**
 * Sa-Token 工具类
 *
 * @author Silas Yan 2025-04-06:06:31
 */
public class SaTokenUtil {

	/**
	 * 获取当前登录用户ID
	 *
	 * @return 当前登录用户ID
	 */
	public static Long getUserId() {
		ThrowUtil.tif(!StpUtil.isLogin(), RespCode.NOT_LOGIN);
		return StpUtil.getLoginIdAsLong();
	}
}
