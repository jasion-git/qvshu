package com.hivacation.webapp.webchat.action.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.common.TimeUtils;
import com.hivacation.webapp.webchat.action.Action;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.eventmsg.ScanEvent;

public class Action_bangkanjia implements Action {

	public static String KEY="kj_";
	
	private String paramStr;
	
	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	@Override
	public Object excute(BaseMsg msg) {
		ScanEvent event=(ScanEvent) msg;
		String webUrl=PropertiesUtils.getString("web.url");
		String url=webUrl+"/app/action/bangkanjia2.html?p=1";
		
		if(StringUtils.isNotEmpty(paramStr)&&paramStr.contains(",")){
			String[]items=paramStr.split(",");
			String actionId=items[0];
			String houseId=items[1];
			String friendOpenId=items[2];
			
			url+="&openId="+event.getFromUserName()+"&actionId="+actionId+"&houseId="+houseId+"&friendOpenId="+friendOpenId;
		}
		
		String content="0元住别墅，砍价行动。正在进行中...\n"
				+"立即帮朋友砍价\n"
				+"<a href='"+url+"'>帮好友砍价>></a>";
		
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

}
