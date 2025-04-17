package com.silas.init.module.main.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.silas.init.common.exception.ThrowUtil;
import com.silas.init.common.redis.RedisUtil;
import com.silas.init.common.response.RespCode;
import com.silas.init.constants.KeyConstant;
import com.silas.init.constants.TextConstant;
import com.silas.init.manager.EmailManager;
import com.silas.init.module.main.entity.request.SendEmailCodeRequest;
import com.silas.init.module.main.entity.vo.CaptchaVO;
import com.silas.init.module.main.service.MainService;
import com.silas.init.module.user.entity.DO.User;
import com.silas.init.module.user.service.UserService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Main 业务服务实现
 *
 * @author Silas Yan 2025-04-06:00:56
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

	private final RedisUtil redisUtil;
	private final EmailManager emailManager;

	private final UserService userService;

	/**
	 * 发送邮箱验证码
	 *
	 * @param sendEmailCodeRequest 发送邮箱验证码请求
	 * @return 邮箱验证码KEY
	 */
	@Override
	public String sendEmailCode(SendEmailCodeRequest sendEmailCodeRequest) {
		String userEmail = sendEmailCodeRequest.getUserEmail();
		boolean exists = userService.exists(new LambdaQueryWrapper<User>().eq(User::getUserEmail, userEmail));
		ThrowUtil.tif(exists, RespCode.FAILED, TextConstant.ERROR_EMAIL_REGISTERED);
		String code = RandomUtil.randomNumbers(4);
		emailManager.sendEmailAsCode(userEmail, TextConstant.INFO_REGISTER_CODE_TITLE, code);
		String codeKey = UUID.randomUUID().toString();
		// 把验证码存入 Redis 并且设置 5 分钟过期
		redisUtil.set(String.format(KeyConstant.PREFIX_EMAIL_CODE, codeKey), code, 5, TimeUnit.MINUTES);
		return codeKey;
	}

	/**
	 * 获取图形验证码
	 *
	 * @return CaptchaVO
	 */
	@Override
	public CaptchaVO getCaptcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
		specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
		String captchaCode = specCaptcha.text().toLowerCase();
		String captchaImage = specCaptcha.toBase64();
		String captchaKey = UUID.randomUUID().toString();
		// 把验证码存入 Redis 并且设置 1 分钟过期
		redisUtil.set(String.format(KeyConstant.PREFIX_CAPTCHA_CODE, captchaKey), captchaCode, 1, TimeUnit.MINUTES);
		CaptchaVO captchaVO = new CaptchaVO();
		captchaVO.setCaptchaKey(captchaKey);
		captchaVO.setCaptchaImage(captchaImage);
		return captchaVO;
	}
}
