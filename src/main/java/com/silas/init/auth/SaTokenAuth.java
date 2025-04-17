package com.silas.init.auth;

import cn.dev33.satoken.stp.StpInterface;

import java.util.List;

/**
 * Sa-Token 权限
 *
 * @author Silas Yan 2025-04-05:21:17
 */
public class SaTokenAuth implements StpInterface {
	@Override
	public List<String> getPermissionList(Object o, String s) {
		return List.of();
	}

	@Override
	public List<String> getRoleList(Object o, String s) {
		return List.of();
	}
}
