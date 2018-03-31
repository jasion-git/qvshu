package com.hivacation.webapp.webchat.msg.eventmsg;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.dto.UserInfo;
import com.hivacation.webapp.webchat.job.CreateUserJob;
import com.hivacation.webapp.webchat.msg.MessageUtils;
import com.hivacation.webapp.webchat.msg.Msg;
import com.hivacation.webapp.webchat.msg.MsgInterviewee;

public class SubscribeEvent extends ScanEvent implements Msg {

	protected Logger log=Logger.getLogger(this.getClass());
	
	@Override
	public Object invoke(MsgInterviewee interview) {
		//场景值
		String eventKey=this.getEventKey();
		log.info("关注事件eventKey="+eventKey);
		
		String msg="";
		String openId=this.getFromUserName();
		
		ScanEvent scanEven=new ScanEvent();
		scanEven.setEventKey(this.getEventKey());
		scanEven.setFromUserName(this.getFromUserName());
		scanEven.setToUserName(this.getToUserName());
		Object scanRet=scanEven.invoke(interview);
		if(scanRet!=null){
			msg=scanRet.toString();
		}
		
		if(StringUtils.isEmpty(msg)){
			//发送一条默认的关注消息
			String webUrl=PropertiesUtils.getString("web.url");
			MessageUtils.genTextMsg(this.getToUserName(), openId, 
					"<a href='"+webUrl+"/app/index.html?openId="+this.getFromUserName()+"'>欢迎来到去别墅</a>");
		}
		
		//执行关注后的动作，新增用户
		UserInfo user=scanEven.getUser();
		user.setAccount(openId);
		user.setName("微信关注用户");
		user.setPassword("abc0123456789");
		CreateUserJob job=new CreateUserJob(user);
		job.start();
		
		return msg;
	}

}
