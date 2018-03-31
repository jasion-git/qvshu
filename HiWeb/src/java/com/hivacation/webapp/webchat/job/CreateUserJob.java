package com.hivacation.webapp.webchat.job;

import org.apache.log4j.Logger;

import com.hivacation.webapp.biz.service.UserService;
import com.hivacation.webapp.common.SpringContextHolder;
import com.hivacation.webapp.dto.UserInfo;

public class CreateUserJob extends Thread {

	protected Logger log=Logger.getLogger(this.getClass());
	
	private UserInfo user;
	
	public CreateUserJob(UserInfo user){
		this.user=user;
	}
	
	public void run(){
		//微信关注，新增用户
		UserService userService=SpringContextHolder.getBean("userService");
		//先判断用户是否存在
		UserInfo userInDB=new UserInfo();
		userInDB.setWxOpenId(user.getWxOpenId());
		userInDB=userService.getUser(userInDB);
		if(userInDB==null){
			//新增用户
			log.info("微信关注，新增用户"+user.getWxOpenId());
			userService.addUser(user);
		}else{
			//更新用户
			log.info("微信关注，更新用户"+user.getWxOpenId());
			if(userInDB.getProxyUser1Id()==null){
				//一级代理人Id为空，则此用户还没绑定代理人，则需要更新代理人Id
				UserInfo updateUser=new UserInfo();
				updateUser.setId(userInDB.getId());
				updateUser.setProxyUser1Id(user.getProxyUser1Id());
				updateUser.setProxyUser2Id(user.getProxyUser2Id());
				userService.updateUser(updateUser);
			}
		}
		
	}
}
