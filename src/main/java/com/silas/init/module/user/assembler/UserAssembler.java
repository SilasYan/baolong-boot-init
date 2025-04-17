package com.silas.init.module.user.assembler;

import com.silas.init.module.user.entity.DO.User;
import com.silas.init.module.user.entity.vo.UserVO;
import org.springframework.beans.BeanUtils;

/**
 * 转换类
 *
 * @author Silas Yan 2025-04-14:22:56
 */
public class UserAssembler {

	/**
	 * 实体 <=> VO
	 *
	 * @param obj 实体
	 * @return VO
	 */
	public static UserVO toVO(User obj) {
		if (obj == null) {
			return null;
		}
		UserVO vo = new UserVO();
		BeanUtils.copyProperties(obj, vo);
		return vo;
	}
}
