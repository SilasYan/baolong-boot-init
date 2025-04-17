package com.silas.init.manager;

import com.silas.init.common.exception.BusinessException;
import com.silas.init.common.response.RespCode;
import com.silas.init.common.utils.EmailUtil;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮箱处理服务
 *
 * @author Silas Yan 2025-04-06:01:19
 */
@Slf4j
@Component
public class EmailManager {
	@Resource
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.nickname}")
	private String nickname;

	@Value("${spring.mail.username}")
	private String sender;

	/**
	 * 发送邮箱验证码
	 *
	 * @param recipient 收件人
	 * @param subject   主题
	 * @param code      验证码
	 */
	public void sendEmailAsCode(String recipient, String subject, String code) {
		Map<String, Object> map = new HashMap<>(8);
		map.put("code", code);
		log.info("[邮件处理服务] 发送验证码 [{}] => 邮箱 [{}]", code, recipient);
		sendEmailAsCode(recipient, subject, map);
	}

	/**
	 * 发送邮箱验证码
	 */
	@Async(value = "emailThreadPool")
	public void sendEmailAsCode(String recipient, String subject, Map<String, Object> contentMap) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			// 组合邮箱发送的内容
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			// 设置邮件发送者
			messageHelper.setFrom(nickname + "<" + sender + ">");
			// 设置邮件接收者
			messageHelper.setTo(recipient);
			// 设置邮件标题
			messageHelper.setSubject(subject);
			// 设置邮件内容
			messageHelper.setText(EmailUtil.emailContentTemplate("email/SendCode.html", contentMap), true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			log.error("[邮件处理服务] 发送验证码失败", e);
			throw new BusinessException(RespCode.SYSTEM_ERROR, "验证码发送失败");
		}
	}

	/**
	 * 发送注册成功邮件
	 */
	public void sendEmailAsRegisterSuccess(String recipient, String subject, String password) {
		Map<String, Object> map = new HashMap<>(8);
		map.put("password", password);
		log.info("[邮件处理服务] 发送验证码 [{}] => 邮箱 [{}]", password, recipient);
		sendEmailAsRegisterSuccess(recipient, subject, map);
	}

	/**
	 * 发送注册成功邮件
	 */
	@Async(value = "emailThreadPool")
	public void sendEmailAsRegisterSuccess(String recipient, String subject, Map<String, Object> contentMap) {
		try {
			// 创建MIME消息
			MimeMessage message = javaMailSender.createMimeMessage();
			// 组合邮箱发送的内容
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			// 设置邮件发送者
			messageHelper.setFrom(nickname + "<" + sender + ">");
			// 设置邮件接收者
			messageHelper.setTo(recipient);
			// 设置邮件主题
			messageHelper.setSubject(subject);
			// 设置邮件内容
			messageHelper.setText(EmailUtil.emailContentTemplate("templates/RegisterSuccess.html", contentMap), true);
			// 发送邮件
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new BusinessException(RespCode.SYSTEM_ERROR, "邮箱发送失败");
		}
	}
}
