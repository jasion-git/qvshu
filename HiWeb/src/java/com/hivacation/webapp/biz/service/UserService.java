package com.hivacation.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.AuthInfo;
import com.hivacation.webapp.dto.UserInfo;


public interface UserService {

	public Map<String,Object>test(Map<String,Object>param);
	/**
	 * 根据用户账号获取用户拥有的权限
	 * @param param
	 * @return
	 */
	public List<AuthInfo>getAuthListByAccount(Map<String,Object>param);
	
	public UserInfo getUserByAccount(UserInfo user,boolean loadDetails);
	
	public UserInfo getUser(UserInfo user);
	public int addUser(UserInfo user);
	public int updateUser(UserInfo user);
}
