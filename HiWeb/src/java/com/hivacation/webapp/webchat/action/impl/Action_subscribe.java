package com.hivacation.webapp.webchat.action.impl;

import com.hivacation.webapp.webchat.action.Action;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.eventmsg.SubscribeEvent;

public class Action_subscribe implements Action {

	@Override
	public Object excute(BaseMsg msg) {
		SubscribeEvent event=(SubscribeEvent) msg;
		InsertJob job=new InsertJob(event.getFromUserName());
		job.start();
		return null;
	}

}
