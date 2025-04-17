package com.silas.init.module.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silas.init.auth.SaTokenUtil;
import com.silas.init.common.enums.BaseEnum;
import com.silas.init.common.exception.BusinessException;
import com.silas.init.common.exception.ThrowUtil;
import com.silas.init.common.page.PageRequest;
import com.silas.init.common.page.PageResponse;
import com.silas.init.common.redis.RedisUtil;
import com.silas.init.common.response.RespCode;
import com.silas.init.common.utils.LambdaUtil;
import com.silas.init.common.utils.ServletUtil;
import com.silas.init.constants.BaseConstant;
import com.silas.init.constants.KeyConstant;
import com.silas.init.constants.TextConstant;
import com.silas.init.manager.EmailManager;
import com.silas.init.module.user.entity.DO.User;
import com.silas.init.module.user.entity.enums.UserDisabledEnum;
import com.silas.init.module.user.entity.enums.UserRoleEnum;
import com.silas.init.module.user.entity.request.UserBanRequest;
import com.silas.init.module.user.entity.request.UserLoginRequest;
import com.silas.init.module.user.entity.request.UserQueryRequest;
import com.silas.init.module.user.entity.request.UserRegisterRequest;
import com.silas.init.module.user.entity.request.UserUpdatePasswordRequest;
import com.silas.init.module.user.entity.request.UserUpdateRequest;
import com.silas.init.module.user.entity.vo.UserInfoVO;
import com.silas.init.module.user.entity.vo.UserVO;
import com.silas.init.module.user.mapper.UserMapper;
import com.silas.init.module.user.service.UserLoginLogService;
import com.silas.init.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户表 (user) - 业务服务实现
 *
 * @author Baolong 2025-04-06 00:23:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	private final RedisUtil redisUtil;
	private final EmailManager emailManager;
	private final TransactionTemplate transactionTemplate;

	private final UserLoginLogService userLoginLogService;

	/**
	 * 用户注册
	 *
	 * @param userRegisterRequest 用户注册请求
	 * @return 成功
	 */
	@Override
	public Boolean register(UserRegisterRequest userRegisterRequest) {
		String redisKey = String.format(KeyConstant.PREFIX_EMAIL_CODE, userRegisterRequest.getCodeKey());
		String code = redisUtil.get(redisKey);
		if (StrUtil.isEmpty(code) || !code.equals(userRegisterRequest.getCodeValue())) {
			throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_CODE);
		}
		String userEmail = userRegisterRequest.getUserEmail();
		boolean exists = this.exists(new LambdaQueryWrapper<User>().eq(User::getUserEmail, userEmail));
		ThrowUtil.tif(exists, RespCode.FAILED, TextConstant.ERROR_EMAIL_REGISTERED);
		// 构建参数
		User user = new User();
		user.setUserEmail(userEmail);
		// 参数填充
		this.fillUserDefaultField(user);
		// 设置密码
		String password = RandomUtil.randomString(8);
		user.setUserPassword(this.encryptPassword(password));
		boolean result = this.save(user);
		ThrowUtil.tif(!result, RespCode.ERROR_OPERATION, TextConstant.ERROR_REGISTER);
		redisUtil.delete(redisKey);
		emailManager.sendEmailAsRegisterSuccess(userEmail, TextConstant.INFO_REGISTER_SUCCESS_TITLE, password);
		return true;
	}

	/**
	 * 填充用户默认字段
	 *
	 * @param user 用户对象
	 */
	@Override
	public void fillUserDefaultField(User user) {
		user.setUserAccount(user.getUserEmail());
		user.setUserName("用户_" + RandomUtil.randomString(6));
		user.setUserRole(UserRoleEnum.USER.getKey());
		user.setUserProfile("用户暂未填写个人简介~");
	}

	/**
	 * 加密密码
	 *
	 * @param password 原始密码
	 * @return 加密后的密码
	 */
	@Override
	public String encryptPassword(String password) {
		return DigestUtils.md5DigestAsHex(("silas" + password).getBytes());
	}

	/**
	 * 用户登录
	 *
	 * @param userLoginRequest 用户登录请求
	 * @return Token
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String login(UserLoginRequest userLoginRequest) {
		String account = userLoginRequest.getAccount();
		User user = this.getOne(new LambdaQueryWrapper<User>()
				.eq(User::getUserAccount, account).or(qw -> qw.eq(User::getUserEmail, account))
		);
		if (user == null) {
			log.error("[用户登录] 登录失败! {}", TextConstant.ERROR_USER_OR_PASSWORD);
			throw new BusinessException(RespCode.ERROR_DATA, TextConstant.ERROR_USER_OR_PASSWORD);
		}
		if (UserDisabledEnum.isDisabled(user.getIsDisabled())) {
			log.error("[用户登录] 登录失败! {}", TextConstant.INFO_USER_DISABLED);
			throw new BusinessException(RespCode.ERROR_FORBIDDEN, TextConstant.INFO_USER_DISABLED);
		}
		if (!user.getUserPassword().equals(this.encryptPassword(userLoginRequest.getUserPassword()))) {
			log.error("[用户登录] 登录失败! {}", TextConstant.ERROR_USER_OR_PASSWORD);
			throw new BusinessException(RespCode.ERROR_DATA, TextConstant.ERROR_USER_OR_PASSWORD);
		}

		Long userId = user.getId();

		// 登录态保存以及信息存入缓存
		StpUtil.login(userId);
		redisUtil.set(String.format(KeyConstant.PREFIX_USER_INFO, userId), user, KeyConstant.USER_INFO_TIME, TimeUnit.DAYS);
		// 异步记录日志
		userLoginLogService.recordLoginLog(userId, new Date(), ServletUtil.getIp(), ServletUtil.getHeader("User-Agent"));
		return StpUtil.getTokenValue();
	}

	/**
	 * 用户注销
	 */
	@Override
	public void logout() {
		Long userId = SaTokenUtil.getUserId();
		String redisKey = String.format(KeyConstant.PREFIX_USER_INFO, userId);
		boolean result = redisUtil.delete(redisKey);
		if (result) {
			StpUtil.logout(userId);
			log.info("[用户注销] 清除用户 [{}] 登录态成功!", userId);
		} else {
			log.error("[用户注销] 清除用户 [{}] 登录态失败!", userId);
		}
	}

	/**
	 * 获取登录用户信息
	 *
	 * @return 用户信息
	 */
	@Override
	public UserInfoVO getLoginUserInfo() {
		Long userId = SaTokenUtil.getUserId();
		String redisKey = String.format(KeyConstant.PREFIX_USER_INFO, userId);
		User user = redisUtil.get(redisKey);
		UserInfoVO userInfoVO = new UserInfoVO();
		if (user != null) {
			BeanUtil.copyProperties(user, userInfoVO);
			log.info("[获取用户信息] 从 Redis 中获取用户 [{}] 信息!", userId);
		} else {
			user = this.getById(userId);
			ThrowUtil.tif(user == null, RespCode.NOT_LOGIN);
			BeanUtil.copyProperties(user, userInfoVO);
			redisUtil.set(redisKey, user, KeyConstant.USER_INFO_TIME, TimeUnit.DAYS);
			log.info("[获取用户信息] 从 MySQL 中获取用户 [{}] 信息!", userId);
		}
		return userInfoVO;
	}

	/**
	 * 更新用户信息
	 *
	 * @param userUpdateRequest 用户更新请求
	 * @return 成功
	 */
	@Override
	public Boolean updateInfo(UserUpdateRequest userUpdateRequest) {
		User user = new User();
		Long userId = userUpdateRequest.getId();
		user.setId(userId);
		String userName = userUpdateRequest.getUserName();
		if (StrUtil.isNotBlank(userName)) {
			user.setUserName(userName);
		}
		String userAccount = userUpdateRequest.getUserAccount();
		if (StrUtil.isNotBlank(userAccount)) {
			// 校验 userAccount 是否重复
			if (this.count(new LambdaQueryWrapper<User>().eq(User::getUserAccount, userAccount)) > BaseConstant.ZERO) {
				log.error("[更新用户信息] 用户 [{}] 账号 [{}] 重复!", userId, userAccount);
				throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_USER_ACCOUNT_EXIST);
			}
			// 校验 userAccount 是否以数字开头并且长度是否小于 5
			if (userAccount.matches(BaseConstant.REGULAR_PREFIX_NUMBER) || userAccount.length() < BaseConstant.FIVE) {
				log.error("[更新用户信息] 用户 [{}] 账号 [{}] 格式错误!", userId, userAccount);
				throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_USER_ACCOUNT_FORMAT);
			}
			user.setUserAccount(userAccount);
		}
		String userPhone = userUpdateRequest.getUserPhone();
		if (StrUtil.isNotBlank(userPhone)) {
			// 校验 userPhone 是否重复
			if (this.count(new LambdaQueryWrapper<User>().eq(User::getUserPhone, userPhone)) > BaseConstant.ZERO) {
				log.error("[更新用户信息] 用户 [{}] 手机号 [{}] 重复!", userId, userPhone);
				throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_USER_PHONE_EXIST);
			}
			// 校验 userPhone 是否以数字开头并且长度是否小于 11
			if (userPhone.matches(BaseConstant.REGULAR_PREFIX_NUMBER) || userPhone.length() < BaseConstant.ELEVEN) {
				log.error("[更新用户信息] 用户 [{}] 手机号 [{}] 格式错误!", userId, userPhone);
				throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_USER_PHONE_FORMAT);
			}
			user.setUserPhone(userPhone);
		}
		String userProfile = userUpdateRequest.getUserProfile();
		// 校验 userProfile 的长度是否大于 250
		if (StrUtil.isNotBlank(userProfile)) {
			if (userProfile.length() > BaseConstant.TWO_HUNDRED_FIFTY) {
				log.error("[更新用户信息] 用户 [{}] 用户简介 [{}] 长度错误!", userId, userProfile);
				throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_USER_PROFILE_LENGTH);
			}
			user.setUserProfile(userProfile);
		}
		boolean result = this.updateById(user);
		if (!result) {
			log.error("[更新用户信息] 用户 [{}] 更新失败!", userId);
			throw new BusinessException(RespCode.ERROR_OPERATION, TextConstant.ERROR_UPDATE_INFO);
		}
		return true;
	}

	/**
	 * 修改用户密码
	 *
	 * @param userUpdatePasswordRequest 用户修改密码请求
	 * @return 成功
	 */
	@Override
	public Boolean updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest) {
		User user = new User();
		Long userId = userUpdatePasswordRequest.getId();
		user.setId(userId);
		String oldPassword = userUpdatePasswordRequest.getOldPassword();
		// 判断原密码是否正确
		if (!this.encryptPassword(oldPassword).equals(this.getById(userId).getUserPassword())) {
			log.error("[修改用户密码] 用户 [{}] 原密码错误!", userId);
			throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_OLD_PASSWORD);
		}
		String newPassword = userUpdatePasswordRequest.getNewPassword();
		user.setUserPassword(this.encryptPassword(newPassword));
		boolean result = this.updateById(user);
		if (!result) {
			log.error("[修改用户密码] 用户 [{}] 修改密码失败!", userId);
			throw new BusinessException(RespCode.ERROR_OPERATION, TextConstant.ERROR_UPDATE_PASSWORD);
		}
		return true;
	}

	/**
	 * 封禁用户
	 *
	 * @param userBanRequest 用户禁用请求
	 * @return 成功
	 */
	@Override
	public String banUserAsAdmin(UserBanRequest userBanRequest) {
		Integer status = userBanRequest.getStatus();
		List<Integer> keys = BaseEnum.keys(UserDisabledEnum.class);
		if (!keys.contains(status)) {
			log.error("[封禁用户] 用户状态 [{}] 不存在!", status);
			throw new BusinessException(RespCode.ERROR_PARAMETER, TextConstant.ERROR_STATUS);
		}
		boolean result = this.update(new LambdaUpdateWrapper<User>()
				.eq(User::getId, userBanRequest.getId())
				.set(User::getIsDisabled, status)
		);
		if (!result) {
			throw new BusinessException(RespCode.ERROR_OPERATION, TextConstant.ERROR_BAN_USER);
		}
		return status.equals(UserDisabledEnum.NORMAL.getKey()) ? "解禁成功" : "封禁成功";
	}

	/**
	 * 获取查询条件构造器
	 *
	 * @param queryRequest 查询请求对象
	 * @return 查询条件构造器
	 */
	@SneakyThrows
	@Override
	public LambdaQueryWrapper<User> lambdaQueryWrapper(UserQueryRequest queryRequest) {
		Long id = queryRequest.getId();
		String userAccount = queryRequest.getUserAccount();
		String userEmail = queryRequest.getUserEmail();
		String userPhone = queryRequest.getUserPhone();
		String userName = queryRequest.getUserName();
		String userProfile = queryRequest.getUserProfile();
		String userRole = queryRequest.getUserRole();
		Integer isDisabled = queryRequest.getIsDisabled();
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(id), User::getId, id);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userAccount), User::getUserAccount, userAccount);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userEmail), User::getUserEmail, userEmail);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userPhone), User::getUserPhone, userPhone);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userName), User::getUserName, userName);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userProfile), User::getUserProfile, userProfile);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userRole), User::getUserRole, userRole);
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(isDisabled), User::getIsDisabled, isDisabled);

		String startEditTime = queryRequest.getStartEditTime();
		String endEditTime = queryRequest.getEndEditTime();
		if (StrUtil.isNotEmpty(startEditTime) && StrUtil.isNotEmpty(endEditTime)) {
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startEditTime);
			Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endEditTime);
			lambdaQueryWrapper.ge(ObjUtil.isNotEmpty(startTime), User::getEditTime, startTime);
			lambdaQueryWrapper.lt(ObjUtil.isNotEmpty(endTime), User::getEditTime, endTime);
		}

		// 处理排序规则
		if (queryRequest.isMultipleSort()) {
			List<PageRequest.Sort> sorts = queryRequest.getSorts();
			if (CollUtil.isNotEmpty(sorts)) {
				sorts.forEach(sort -> {
					String sortField = sort.getField();
					boolean sortAsc = sort.isAsc();
					lambdaQueryWrapper.orderBy(
							StrUtil.isNotEmpty(sortField), sortAsc, LambdaUtil.getLambda(User.class, sortField)
					);
				});
			}
		} else {
			PageRequest.Sort sort = queryRequest.getSort();
			if (sort != null) {
				String sortField = sort.getField();
				boolean sortAsc = sort.isAsc();
				lambdaQueryWrapper.orderBy(
						StrUtil.isNotEmpty(sortField), sortAsc, LambdaUtil.getLambda(User.class, sortField)
				);
			} else {
				lambdaQueryWrapper.orderByDesc(User::getCreateTime);
			}
		}
		return lambdaQueryWrapper;
	}

	/**
	 * 获取用户分页
	 *
	 * @param userQueryRequest 用户查询请求
	 * @return 用户分页
	 */
	@Override
	public PageResponse<UserVO> getUserPage(UserQueryRequest userQueryRequest) {
		Page<User> page = this.page(userQueryRequest.page(User.class), this.lambdaQueryWrapper(userQueryRequest));
		return PageResponse.to(page, UserVO.class);
	}
}
