package com.hivacation.webapp.webchat.msg.eventmsg;

import com.hivacation.webapp.webchat.action.impl.Action_unsubscribe;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.Msg;
import com.hivacation.webapp.webchat.msg.MsgInterviewee;

public class UnSubscribeEvent extends BaseMsg implements Msg {

	private String event;
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public Object invoke(MsgInterviewee interview) {
		Action_unsubscribe action=new Action_unsubscribe();
		return action.excute(this);
	}

}
