package com.hivacation.webapp.webchat.action.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.common.SpringContextHolder;
import com.hivacation.webapp.common.TimeUtils;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.webchat.action.Action;
import com.hivacation.webapp.webchat.msg.BaseMsg;

public class Action_kanjia implements Action {

	public static String SCENE_KEY="kanjia";
	
	@Override
	public Object excute(BaseMsg msg) {
		//返回一段报文，提示用户点击链接
		String webUrl=PropertiesUtils.getString("web.url");
		/*String appId="wx25684d6a1869bec7";
		String redirectUrl=webUrl+"/hiweb/wx/gotoUrl.json";
		String pageKey="action0kanjia0html";//就要跳转的页面,0为分割线
		try {
			redirectUrl=URLEncoder.encode(redirectUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		/*
		 * https://open.weixin.qq.com/connect/oauth2/authorize?
		 * appid=wx520c15f417810387
		 * &redirect_uri=https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60
		 * &response_type=code
		 * &scope=snsapi_base
		 * &state=123#wechat_redirect 
		 * */
		/*String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+redirectUrl
				+"&response_type=code&scope=snsapi_userinfo&state="+pageKey+"#wechat_redirect";*/
		
		//直接去到活动页面，因为是在会话内的消息
		String url=webUrl+"/app/action/kanjiaList.html?p=1";
		//加上昵称和头像
		String openId=msg.getFromUserName();
		url+="&self="+openId;//加上自己的openId
		//根据openId获取保存下载的昵称和头像
		CommonService commonService=SpringContextHolder.getBean("commonService");
		SubscribeRecord record=new SubscribeRecord();
		record.setOpenId(openId);
		record=commonService.getSubscribeRecord(record);
		if(record!=null){
			try {
				url+="&nickName="+record.getName()+"&headImgUrl="+URLEncoder.encode(record.getHeadUrl(), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String content="砍价活动详情:\n"
				+"请点击链接:<a href='"+url+"'>砍价活动</a>";
		
		Document doc=DocumentHelper.createDocument();
		Element xml=doc.addElement( "xml" );
		xml.addElement("ToUserName").addText(msg.getFromUserName());
		xml.addElement("FromUserName").addText(msg.getToUserName());
		xml.addElement("CreateTime").addText(TimeUtils.date2String(
				new Date(), TimeUtils.FORMAT_YYYYMMDDHHMMSS));
		xml.addElement("MsgType").addText("text");
		xml.addElement("Content").addText(content);
		return doc.getRootElement().asXML();
	}
	
	public static void main(String[]args)throws Exception{
		String s="http://123.207.248.40";
		System.out.println(URLEncoder.encode(s, "utf-8"));
	}

}
