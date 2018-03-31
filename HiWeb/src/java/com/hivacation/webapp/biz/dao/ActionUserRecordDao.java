package com.hivacation.webapp.biz.dao;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.ActionUserRecord;

public interface ActionUserRecordDao {

	public int addActionUserRecord(ActionUserRecord record);
	public int deleteActionUserRecord(ActionUserRecord record);
	public int updateActionUserRecord(ActionUserRecord record);
	public List<ActionUserRecord>getActionUserRecordList(Map<String,Object>param);
	public int getActionUserRecordListCount(Map<String,Object>param);
	
	public List<Map<String,Object>>getKanJiaFriendList(Map<String,Object>param);
}
