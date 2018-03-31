package com.hivacation.webapp.biz.dao;

import java.util.Map;

import com.hivacation.webapp.dto.UserInfo;

public interface UserDao {

	public Map<String,Object>test(Map<String,Object>param);
	
	public UserInfo getUser(UserInfo user);
	public int addUser(UserInfo user);
	public int updateUser(UserInfo user);
}
