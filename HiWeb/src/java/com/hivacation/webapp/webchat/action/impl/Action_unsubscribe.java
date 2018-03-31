package com.hivacation.webapp.webchat.action.impl;

import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.common.SpringContextHolder;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.webchat.action.Action;
import com.hivacation.webapp.webchat.msg.BaseMsg;
import com.hivacation.webapp.webchat.msg.eventmsg.UnSubscribeEvent;

public class Action_unsubscribe implements Action {

	@Override
	public Object excute(BaseMsg msg) {
		UnSubscribeEvent event=(UnSubscribeEvent) msg;
		CommonService commonService=SpringContextHolder.getBean("commonService");
		SubscribeRecord record=new SubscribeRecord();
		record.setOpenId(event.getFromUserName());
		record.setIsSubscribe(0);
		commonService.updateSubscrebeRecord(record);
		return "";
	}

}
