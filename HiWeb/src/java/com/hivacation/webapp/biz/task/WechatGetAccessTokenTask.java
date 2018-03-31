package com.hivacation.webapp.biz.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSONObject;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.common.HttpUtils;
import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.dto.WeChatGZH;

@Component
public class WechatGetAccessTokenTask {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	//1一小时一次
	@Scheduled(cron="0 0 0/1 * * ?")
	public void excute(){
		log.info("获取微信accessToken");
		String wechatAccount=PropertiesUtils.getString("wechat.gzh");
		WeChatGZH wechat=new WeChatGZH();
		wechat.setAccount(wechatAccount);
		wechat=commonService.getWeChatGZH(wechat);
		if(wechat==null){
			return;
		}
		
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+wechat.getAppId()+"&secret="+wechat.getSecret();
		String ret=HttpUtils.get(url);
		
		log.info(ret);
		//System.out.println(ret);
		/*
		 * {
		 * 	"access_token":"wVnVHl3w7chkf372zuzDR_VUjucS9DOgKGkqYt1nS0BCMH7gSrKXkp8UvnSyL4JdoHlktJgnk8zimsl4kkj8UMfKDBIUczGzkdNap3peIsOSGMvA-H-iwxqoHDsVtSUbVQTjAAABWZ",
		 * 	"expires_in":7200
		 * }
		 * */
		JSONObject json=JSONObject.parseObject(ret);
		Integer expiresIn=json.getInteger("expires_in");
		String accessToken=json.getString("access_token");
		
		wechat.setExpiresIn(expiresIn);
		wechat.setAccessToken(accessToken);
		commonService.updateWeChatGZH(wechat);
	}
	
	public static void main(String[]args){
		String appId="wx25684d6a1869bec7";
		String secret="c65d12efc44d3230a5768f4a1ec47081";
		
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+appId+"&secret="+secret;
		String ret=HttpUtils.get(url);
		System.out.println(ret);
		/*
		 * {"access_token":"Na674YDkpRUqRECaqIINlECKNAXD2cBmnfywhKioTD4rPrFmGJWOdjokn2g16gXqXyapbivad3AVj_QEOdi09eIq_1Rw0NhwO1howY8gAmvOdWuiMJ9ul68PZsXoJOQoLQRaAEASXY","expires_in":7200}
		 * */
	}
}
