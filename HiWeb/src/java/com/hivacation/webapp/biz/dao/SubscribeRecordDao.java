package com.hivacation.webapp.biz.dao;

import com.hivacation.webapp.dto.SubscribeRecord;

public interface SubscribeRecordDao {

	public int addSubscribeRecord(SubscribeRecord record);
	public int deleteSubscribeRecord(SubscribeRecord record);
	public int updateSubscribeRecord(SubscribeRecord record);
	public SubscribeRecord getSubscribeRecord(SubscribeRecord record);
}
