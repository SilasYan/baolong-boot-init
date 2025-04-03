package com.baolong.init.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baolong.init.common.page.PageRequest;
import com.baolong.init.mapper.UserMapper;
import com.baolong.init.model.User;
import com.baolong.init.model.request.UserQueryRequest;
import com.baolong.init.service.UserService;
import com.baolong.init.utils.SFLambdaUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户表 (user) - 业务服务实现
 *
 * @author Baolong 2025-04-03 19:58:46
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	/**
	 * 获取查询条件构造器
	 *
	 * @param userQueryRequest 用户查询请求对象
	 * @return 查询条件构造器
	 */
	@SneakyThrows
	@Override
	public LambdaQueryWrapper<User> lambdaQueryWrapper(UserQueryRequest userQueryRequest) {
		Long id = userQueryRequest.getId();
		String userAccount = userQueryRequest.getUserAccount();
		String userEmail = userQueryRequest.getUserEmail();
		String userPhone = userQueryRequest.getUserPhone();
		String userName = userQueryRequest.getUserName();
		String userProfile = userQueryRequest.getUserProfile();
		String userRole = userQueryRequest.getUserRole();
		String shareCode = userQueryRequest.getShareCode();
		Integer isDisabled = userQueryRequest.getIsDisabled();
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(id), User::getId, id);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userAccount), User::getUserAccount, userAccount);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userEmail), User::getUserEmail, userEmail);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userPhone), User::getUserPhone, userPhone);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userName), User::getUserName, userName);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userProfile), User::getUserProfile, userProfile);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(userRole), User::getUserRole, userRole);
		lambdaQueryWrapper.eq(StrUtil.isNotEmpty(shareCode), User::getShareCode, shareCode);
		lambdaQueryWrapper.eq(ObjUtil.isNotNull(isDisabled), User::getIsDisabled, isDisabled);

		String startEditTime = userQueryRequest.getStartEditTime();
		String endEditTime = userQueryRequest.getEndEditTime();
		if (StrUtil.isNotEmpty(startEditTime) && StrUtil.isNotEmpty(endEditTime)) {
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startEditTime);
			Date endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endEditTime);
			lambdaQueryWrapper.ge(ObjUtil.isNotEmpty(startTime), User::getEditTime, startTime);
			lambdaQueryWrapper.lt(ObjUtil.isNotEmpty(endTime), User::getEditTime, endTime);
		}

		// 处理排序规则
		if (userQueryRequest.isMultipleSort()) {
			List<PageRequest.Sort> sorts = userQueryRequest.getSorts();
			if (CollUtil.isNotEmpty(sorts)) {
				sorts.forEach(sort -> {
					String sortField = sort.getField();
					boolean sortAsc = sort.isAsc();
					lambdaQueryWrapper.orderBy(
							StrUtil.isNotEmpty(sortField), sortAsc, SFLambdaUtil.getSFunction(User.class, sortField)
					);
				});
			}
		} else {
			PageRequest.Sort sort = userQueryRequest.getSort();
			if (sort != null) {
				String sortField = sort.getField();
				boolean sortAsc = sort.isAsc();
				lambdaQueryWrapper.orderBy(
						StrUtil.isNotEmpty(sortField), sortAsc, SFLambdaUtil.getSFunction(User.class, sortField)
				);
			} else {
				lambdaQueryWrapper.orderByDesc(User::getCreateTime);
			}
		}
		return lambdaQueryWrapper;
	}
}
