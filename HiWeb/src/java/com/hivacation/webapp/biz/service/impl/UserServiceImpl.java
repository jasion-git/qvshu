package com.hivacation.webapp.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hivacation.webapp.biz.dao.UserDao;
import com.hivacation.webapp.biz.service.UserService;
import com.hivacation.webapp.dto.AuthInfo;
import com.hivacation.webapp.dto.UserInfo;



@Service("userService")
@Transactional
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

	@Override
	public UserInfo getUser(UserInfo user) {
		return userDao.getUser(user);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addUser(UserInfo user) {
		return userDao.addUser(user);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateUser(UserInfo user) {
		return userDao.updateUser(user);
	}
	
	
	
	
}
