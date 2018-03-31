package com.hivacation.webapp.webchat.msg.commsg;

import com.hivacation.webapp.webchat.action.impl.Action_sayHello;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.Msg;
import com.hivacation.webapp.webchat.msg.MsgInterviewee;

public class TextMsg extends BaseMsg implements Msg{

	private String msgId;
	private String content;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public Object invoke(MsgInterviewee interview) {
		//根据不同的消息体，返回不同回复内容
		
		//罗列不同action
		Action_sayHello ac_sayHello=new Action_sayHello();
		Object ret=ac_sayHello.excute(this);
		
		return ret;
	}
	
}
