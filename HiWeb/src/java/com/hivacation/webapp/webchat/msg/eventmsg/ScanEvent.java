package com.hivacation.webapp.webchat.msg.eventmsg;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.common.SpringContextHolder;
import com.hivacation.webapp.dto.UserInfo;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.MessageUtils;
import com.hivacation.webapp.webchat.msg.Msg;
import com.hivacation.webapp.webchat.msg.MsgInterviewee;

public class ScanEvent extends BaseMsg implements Msg {

	protected Logger log=Logger.getLogger(this.getClass());
	
	private String event;
	private String eventKey;
	private String ticket;
	
	private UserInfo user;
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public Object invoke(MsgInterviewee interview) {
		//场景值
		String enventKey=this.getEventKey();
		log.info("关注事件eventKey="+enventKey);
		
		user=new UserInfo();
		user.setWxOpenId(this.getFromUserName());
		
		/*if(Action_kanjia.SCENE_KEY.equals(this.getEventKey())){
			Action_kanjia action=new Action_kanjia();
			String ret=(String) action.excute(this);
			return ret;
		}else if(this.getEventKey().contains(Action_bangkanjia.KEY)){
			//砍价活动临时二维码
			String key=this.getEventKey();
			CommonService commonService=SpringContextHolder.getBean("commonService");
			String sceneStr=commonService.getStringInCache(key);
			if(StringUtils.isNotEmpty(sceneStr)){
				//sceneStr=1,1,o_DzIs27iqhhWxxgTpqS99XGxPnY
				Action_bangkanjia action=new Action_bangkanjia();
				action.setParamStr(sceneStr);
				return action.excute(this);
			}
		}else */
		
		if(enventKey.startsWith("tempCode_")){
			//为临时二维码的场景值，通过redis获取具体的内容
			CommonService commonService=SpringContextHolder.getBean("commonService");
			String value=commonService.getStringInCache(enventKey);//临时二维码场景值，对应的内容
			if(StringUtils.isNotEmpty(value)){
				//解析回json
				JSONObject json=JSONObject.parseObject(value);
				/*
				 * {
				 * 		action:"createOrder",
				 * 		url:"/app/pay/pay1.html?houseId=24"
				 * 		content:"欢迎预定别墅"
				 * }
				 * 
				 * */
				String content=json.getString("content");
				//解析回正常字符
				try {
					content=URLDecoder.decode(content, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String msg=MessageUtils.genTextMsg(this.getToUserName(), this.getFromUserName(), content);
				return msg;
			}else if(enventKey.startsWith("proxy_")){
				//代理人二维码
				String webUrl=PropertiesUtils.getString("web.url");
				String content="<a href='"+webUrl+"/app/index.html?openId="+this.getFromUserName()+"'>欢迎预定趣别墅</a>";
				String msg=MessageUtils.genTextMsg(this.getToUserName(), this.getFromUserName(), content);
				
				//解析出用户信息
				String userStr=enventKey.replace("proxy_", "");
				if(StringUtils.isNotEmpty(userStr)){
					String[]userIds=userStr.split("-");
					if(userIds!=null&&userIds.length>0){
						if(userIds.length==1){
							//一级代理人
							Integer proxyUserId=Integer.parseInt(userIds[0]);
							user.setProxyUser1Id(proxyUserId);
						}else if(userIds.length==2){
							//二级代理人
							Integer proxyUser1Id=Integer.parseInt(userIds[0]);
							Integer proxyUser2Id=Integer.parseInt(userIds[1]);
							user.setProxyUser1Id(proxyUser1Id);
							user.setProxyUser2Id(proxyUser2Id);
						}
					}
				}
				
				return msg;
			}
		}
		return "";
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

}
