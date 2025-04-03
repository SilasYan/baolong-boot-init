package com.baolong.init.controller;

import com.baolong.init.common.response.BaseResponse;
import com.baolong.init.common.response.RUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main 控制器
 *
 * @author Silas Yan 2025-04-02:23:25
 */
@Tag(name = "Main 控制器", description = "Main 相关接口")
@RestController
@RequestMapping("/")
public class MainController {

	@Operation(summary = "心跳检测", description = "用于检测服务是否正常")
	@GetMapping("/health")
	public BaseResponse<?> health() {
		return RUtils.success();
	}
}
