package com.silas.init.controller;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.silas.init.common.exception.BusinessException;
import com.silas.init.common.exception.ThrowUtil;
import com.silas.init.common.response.BaseResponse;
import com.silas.init.common.response.RespCode;
import com.silas.init.common.response.Result;
import com.silas.init.constants.BaseConstant;
import com.silas.init.constants.TextConstant;
import com.silas.init.module.user.entity.request.UserLoginRequest;
import com.silas.init.module.user.entity.request.UserRegisterRequest;
import com.silas.init.module.user.entity.request.UserUpdatePasswordRequest;
import com.silas.init.module.user.entity.request.UserUpdateRequest;
import com.silas.init.module.user.entity.vo.UserInfoVO;
import com.silas.init.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author Silas Yan 2025-04-06:00:31
 */
@Tag(name = "UserApi", description = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@Operation(summary = "用户注册", description = "用户注册")
	@PostMapping("/register")
	public BaseResponse<Boolean> register(@RequestBody UserRegisterRequest userRegisterRequest) {
		ThrowUtil.nullIf(userRegisterRequest, TextConstant.ERROR_PARAMETER);
		ThrowUtil.checkEmail(userRegisterRequest.getUserEmail(), "邮箱格式错误!");
		String codeKey = userRegisterRequest.getCodeKey();
		ThrowUtil.tif(StrUtil.isBlank(codeKey), RespCode.ERROR_PARAMETER, "验证码KEY不能为空!");
		String codeValue = userRegisterRequest.getCodeValue();
		ThrowUtil.tif(StrUtil.isBlank(codeValue), RespCode.ERROR_PARAMETER, "验证码不能为空!");
		return Result.success("注册成功!", userService.register(userRegisterRequest));
	}

	@Operation(summary = "用户登录", description = "用户登录")
	@PostMapping("/login")
	public BaseResponse<String> login(@RequestBody UserLoginRequest userLoginRequest) {
		ThrowUtil.nullIf(userLoginRequest, TextConstant.ERROR_PARAMETER);
		String account = userLoginRequest.getAccount();
		ThrowUtil.tif(StrUtil.isBlank(account), RespCode.ERROR_PARAMETER, "请输入账号/邮箱!");
		if (account.length() < BaseConstant.FIVE) {
			throw new BusinessException(RespCode.ERROR_PARAMETER, "账号/邮箱长度不能小于5位!");
		}
		String userPassword = userLoginRequest.getUserPassword();
		ThrowUtil.tif(StrUtil.isBlank(userPassword), RespCode.ERROR_PARAMETER, "请输入密码!");
		if (userPassword.length() < BaseConstant.EIGHT) {
			throw new BusinessException(RespCode.ERROR_PARAMETER, "密码长度不能小于8位!");
		}
		// String captchaKey = userLoginRequest.getCaptchaKey();
		// ThrowUtil.tif(StrUtil.isBlank(captchaKey), RespCode.ERROR_PARAMETER, "图形验证码KEY不能为空!");
		// String captchaValue = userLoginRequest.getCaptchaValue();
		// ThrowUtil.tif(StrUtil.isBlank(captchaValue), RespCode.ERROR_PARAMETER, "图形验证码不能为空!");
		return Result.success("登录成功!", userService.login(userLoginRequest));
	}

	@Operation(summary = "用户注销", description = "用户注销")
	@PostMapping("/logout")
	public BaseResponse<String> logout() {
		userService.logout();
		return Result.success("注销成功!");
	}

	@Operation(summary = "获取登录用户信息", description = "获取当前登录的用户信息")
	@GetMapping("/info")
	public BaseResponse<UserInfoVO> getLoginUserInfo() {
		return Result.success(userService.getLoginUserInfo());
	}

	@Operation(summary = "更新用户信息", description = "更新用户信息")
	@PostMapping("/update/info")
	public BaseResponse<Boolean> updateInfo(@RequestBody UserUpdateRequest userUpdateRequest) {
		ThrowUtil.nullIf(userUpdateRequest, TextConstant.ERROR_PARAMETER);
		Long userId = userUpdateRequest.getId();
		ThrowUtil.tif(ObjUtil.isEmpty(userId) || userId <= 0, "用户ID不能为空!");
		String userAccount = userUpdateRequest.getUserAccount();
		String userPhone = userUpdateRequest.getUserPhone();
		String userName = userUpdateRequest.getUserName();
		String userAvatar = userUpdateRequest.getUserAvatar();
		String userProfile = userUpdateRequest.getUserProfile();
		ThrowUtil.tif(StrUtil.hasEmpty(userAccount, userPhone, userName, userAvatar, userProfile), "无数据更新!");
		return Result.success("更新成功!", userService.updateInfo(userUpdateRequest));
	}

	@Operation(summary = "修改用户密码", description = "修改用户密码")
	@PostMapping("/update/password")
	public BaseResponse<Boolean> updatePassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
		ThrowUtil.nullIf(userUpdatePasswordRequest, TextConstant.ERROR_PARAMETER);
		Long userId = userUpdatePasswordRequest.getId();
		ThrowUtil.tif(ObjUtil.isEmpty(userId) || userId <= 0, "用户ID不能为空!");
		String oldPassword = userUpdatePasswordRequest.getOldPassword();
		String newPassword = userUpdatePasswordRequest.getNewPassword();
		String confirmPassword = userUpdatePasswordRequest.getConfirmPassword();
		ThrowUtil.tif(StrUtil.hasEmpty(oldPassword, newPassword, confirmPassword), "密码不能为空!");
		ThrowUtil.tif(oldPassword.equals(newPassword), "新密码不能与旧密码相同!");
		ThrowUtil.tif(!newPassword.equals(confirmPassword), "两次新密码不一致!");
		return Result.success("更新成功!", userService.updatePassword(userUpdatePasswordRequest));
	}
}
