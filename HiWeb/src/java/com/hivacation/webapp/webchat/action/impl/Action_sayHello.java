package com.hivacation.webapp.webchat.action.impl;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hivacation.webapp.common.PropertiesUtils;
import com.hivacation.webapp.common.TimeUtils;
import com.hivacation.webapp.webchat.action.Action;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.commsg.TextMsg;

public class Action_sayHello implements Action {

	private static String KEY_WORD="你好";
	
	@Override
	public Object excute(BaseMsg msg) {
		TextMsg textMsg=(TextMsg) msg;
		if(KEY_WORD.equals(textMsg.getContent())){
			//生成xml报文
			/*
			 * <xml>
				<ToUserName><![CDATA[toUser]]></ToUserName>
				<FromUserName><![CDATA[fromUser]]></FromUserName>
				<CreateTime>12345678</CreateTime>
				<MsgType><![CDATA[text]]></MsgType>
				<Content><![CDATA[你好]]></Content>
				</xml>
			 * */
			
			String webUrl=PropertiesUtils.getString("web.url");
			String openId=textMsg.getFromUserName();
			Document doc=DocumentHelper.createDocument();
			Element xml=doc.addElement( "xml" );
			xml.addElement("ToUserName").addText(textMsg.getFromUserName());
			xml.addElement("FromUserName").addText(textMsg.getToUserName());
			xml.addElement("CreateTime").addText(TimeUtils.date2String(
					new Date(), TimeUtils.FORMAT_YYYYMMDDHHMMSS));
			xml.addElement("MsgType").addText("text");
			xml.addElement("Content").addText("您好，先生/女士，<a href='"+webUrl+"/app/index.html?openId="+openId+"'>首页</a>");
			
			return doc.getRootElement().asXML();
		}
		return null;
	}
	
	public static void main(String[]args){
		Action_sayHello t=new Action_sayHello();
		TextMsg msg=new TextMsg();
		msg.setContent("你好");
		msg.setFromUserName("xxxxxxxxxxx");
		msg.setToUserName("uuu111111111");
		
		System.out.println(t.excute(msg));
	}

}
