package com.hivacation.webapp.webchat.msg;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hivacation.webapp.common.TimeUtils;

public class MessageUtils {

	public static String genTextMsg(String from,String to,String content){
		Document doc=DocumentHelper.createDocument();
		Element xml=doc.addElement( "xml" );
		xml.addElement("ToUserName").addText(to);
		xml.addElement("FromUserName").addText(from);
		xml.addElement("CreateTime").addText(TimeUtils.date2String(
				new Date(), TimeUtils.FORMAT_YYYYMMDDHHMMSS));
		xml.addElement("MsgType").addText("text");
		xml.addElement("Content").addText(content);
		return doc.getRootElement().asXML();
	}
}
