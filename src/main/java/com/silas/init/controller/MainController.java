package com.silas.init.controller;

import com.silas.init.common.exception.ThrowUtil;
import com.silas.init.common.response.BaseResponse;
import com.silas.init.common.response.Result;
import com.silas.init.constants.TextConstant;
import com.silas.init.module.main.entity.request.SendEmailCodeRequest;
import com.silas.init.module.main.entity.vo.CaptchaVO;
import com.silas.init.module.main.service.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main 接口
 *
 * @author Silas Yan 2025-04-06:00:31
 */
@Tag(name = "MainApi", description = "Main 相关接口")
@RestController
public class MainController {

	@Resource
	private MainService mainService;

	@Operation(summary = "心跳检测", description = "用于检测服务是否正常")
	@GetMapping("/health")
	public BaseResponse<?> health() {
		return Result.success(true);
	}

	@Operation(summary = "发送邮箱验证码", description = "发送邮箱验证码")
	@PostMapping("/send/email/code")
	public BaseResponse<String> sendEmailCode(@RequestBody SendEmailCodeRequest sendEmailCodeRequest) {
		ThrowUtil.nullIf(sendEmailCodeRequest, TextConstant.ERROR_PARAMETER);
		ThrowUtil.checkEmail(sendEmailCodeRequest.getUserEmail(), "邮箱格式错误!");
		return Result.success(mainService.sendEmailCode(sendEmailCodeRequest));
	}

	@Operation(summary = "获取图形验证码", description = "用于注册发送验证码")
	@GetMapping("/captcha")
	public BaseResponse<CaptchaVO> captcha() {
		return Result.success(mainService.getCaptcha());
	}
}
