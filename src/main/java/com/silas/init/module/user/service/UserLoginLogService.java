package com.silas.init.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silas.init.module.user.entity.DO.UserLoginLog;

import java.util.Date;

/**
 * 用户登录日志表 (user_login_log) - 业务服务接口
 *
 * @author Baolong 2025-04-06 03:16:12
 */
public interface UserLoginLogService extends IService<UserLoginLog> {

	/**
	 * 记录登录日志
	 *
	 * @param userId    用户ID
	 * @param loginTime 登录时间
	 * @param loginIp   登录IP
	 * @param userAgent 登录设备信息
	 */
	void recordLoginLog(Long userId, Date loginTime, String loginIp, String userAgent);
}
