package com.baolong.init.controller;

import com.baolong.init.common.response.BaseResponse;
import com.baolong.init.common.response.RUtils;
import com.baolong.init.common.response.RespCode;
import com.baolong.init.model.User;
import com.baolong.init.model.request.UserAddRequest;
import com.baolong.init.model.request.UserQueryRequest;
import com.baolong.init.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author Silas Yan 2025-04-03:20:09
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@Operation(summary = "新增用户", description = "用于用户新增")
	@PostMapping("/add")
	public BaseResponse<?> add(@RequestBody UserAddRequest userAddRequest) {
		User user = new User();
		BeanUtils.copyProperties(userAddRequest, user);
		boolean result = userService.save(user);
		if (result) {
			return RUtils.success("新增成功", user.getId());
		}
		return RUtils.failed(RespCode.FAILED, "新增失败");
	}

	@Operation(summary = "获取用户分页", description = "获取用户分页")
	@PostMapping("/page")
	public BaseResponse<?> page(@RequestBody UserQueryRequest userQueryRequest) {
		Page<User> page = userService.page(userQueryRequest.page(User.class), userService.lambdaQueryWrapper(userQueryRequest));
		return RUtils.success(page);
	}
}
