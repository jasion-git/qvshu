package com.hishu.vacation.biz.service;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.AuthInfo;
import com.hishu.vacation.dto.UserInfo;


public interface UserService {

	public Map<String,Object>test(Map<String,Object>param);
	/**
	 * 根据用户账号获取用户拥有的权限
	 * @param param
	 * @return
	 */
	public List<AuthInfo>getAuthListByAccount(Map<String,Object>param);
	
	public UserInfo getUserByAccount(UserInfo user,boolean loadDetails);
}
