package com.hishu.vacation.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hishu.vacation.biz.dao.UserDao;
import com.hishu.vacation.biz.service.UserService;
import com.hishu.vacation.dto.AuthInfo;
import com.hishu.vacation.dto.UserInfo;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public Map<String, Object> test(Map<String, Object> param) {
		return userDao.test(param);
	}

	@Override
	public List<AuthInfo> getAuthListByAccount(Map<String, Object> param) {
		//TODO
		List<AuthInfo>list=new ArrayList<AuthInfo>();
		AuthInfo a=new AuthInfo();
		a.setCode("testpermission");
		list.add(a);
		return list;
	}

	@Override
	public UserInfo getUserByAccount(UserInfo user,boolean loadDetails) {
		//TODO
		UserInfo userInDB=new UserInfo();
		userInDB.setAccount("jasion");
		userInDB.setPassword("123456");
		return userInDB;
	}

}
