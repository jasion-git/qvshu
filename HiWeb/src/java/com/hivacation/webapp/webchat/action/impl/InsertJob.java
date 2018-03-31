package com.hivacation.webapp.webchat.action.impl;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.common.SpringContextHolder;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.dto.WeChatGZH;
import com.hivacation.webapp.webchat.common.WeChatUtils;

public class InsertJob extends Thread {

	private String openId;
	
	public InsertJob(String openId){
		this.openId=openId;
	}
	
	@Override
	public void run() {
		CommonService commonService=SpringContextHolder.getBean("commonService");
		//获取access token
		String wechatAccount=PropertiesUtils.getString("wechat.gzh");
		WeChatGZH wechat=new WeChatGZH();
		wechat.setAccount(wechatAccount);
		wechat=commonService.getWeChatGZH(wechat);
		
		String accessToken=null;
		if(wechat!=null){
			accessToken=wechat.getAccessToken();
		}
		if(StringUtils.isNotEmpty(accessToken)){
			//获取用户信息
			String userJsonStr=WeChatUtils.getWechatUserInfo(accessToken,openId);
			/*
			 * {
			    "subscribe": 1,
			    "openid": "oLVPpjqs2BhvzwPj5A-vTYAX4GLc",
			    "nickname": "刺猬宝宝",
			    "sex": 1,
			    "language": "zh_CN",
			    "city": "深圳",
			    "province": "广东",
			    "country": "中国",
			    "headimgurl": "http://wx.qlogo.cn/mmopen/JcDicrZBlREhnNXZRudod9PmibRkIs5K2f1tUQ7lFjC63pYHaXGxNDgMzjGDEuvzYZbFOqtUXaxSdoZG6iane5ko9H30krIbzGv/0",
			    "subscribe_time": 1386160805
			}
			
			{"subscribe":1,"openid":"o_DzIs27iqhhWxxgTpqS99XGxPnY","nickname":"Jasion","sex":1,"language":"zh_CN","city":"深圳","province":"广东","country":"中国","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/XuMnXGzNsnEQBQxAuQznvuJGk78BuQYo6QMTpL3cyOyJEzibw8TiaIDd61micdoNHvTjPWwJD2S8xwkib2iaRLa96Zm9Tjd8VgeAY\/0","subscribe_time":1506706029,"remark":"","groupid":0,"tagid_list":[]}
			 * */
			System.out.println(userJsonStr);
			
			JSONObject json=JSONObject.parseObject(userJsonStr);
			if(json!=null){
				String headImgUrl=json.getString("headimgurl");
				String nickName=json.getString("nickname");
				//先判断记录是否存在
				SubscribeRecord record=new SubscribeRecord();
				record.setOpenId(openId);
				record=commonService.getSubscribeRecord(record);
				if(record!=null){
					//已经存在，则更新
					SubscribeRecord recordUpdate=new SubscribeRecord();
					recordUpdate.setOpenId(openId);
					recordUpdate.setHeadUrl(headImgUrl);
					recordUpdate.setName(nickName);
					recordUpdate.setIsSubscribe(1);
					commonService.updateSubscrebeRecord(record);
				}else{
					//不存在，则插入
					SubscribeRecord recordAdd=new SubscribeRecord();
					recordAdd.setBeSubscribe(wechat.getAccount());
					recordAdd.setHeadUrl(headImgUrl);
					recordAdd.setName(nickName);
					recordAdd.setIsSubscribe(1);
					recordAdd.setOpenId(openId);
					commonService.addSubscribeRecord(recordAdd);
				}
			}
		}
		
		
		
	}
}
