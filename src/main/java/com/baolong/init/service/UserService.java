package com.baolong.init.service;

import com.baolong.init.model.User;
import com.baolong.init.model.request.UserQueryRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户表 (user) - 业务服务接口
 *
 * @author Baolong 2025-04-03 19:58:46
 */
public interface UserService extends IService<User> {

	/**
	 * 获取查询条件构造器
	 *
	 * @param userQueryRequest 用户查询请求对象
	 * @return 查询条件构造器
	 */
	LambdaQueryWrapper<User> lambdaQueryWrapper(UserQueryRequest userQueryRequest);
}
