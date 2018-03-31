package com.hivacation.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.ActionUserRecord;
import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.dto.WeChatGZH;

public interface CommonService {

	public WeChatGZH getWeChatGZH(WeChatGZH wechat);
	public int updateWeChatGZH(WeChatGZH wechat);
	
	public int addSubscribeRecord(SubscribeRecord record);
	public int deleteSubscribeRecord(SubscribeRecord record);
	public int updateSubscrebeRecord(SubscribeRecord record);
	public SubscribeRecord getSubscribeRecord(SubscribeRecord record);
	
	public void setStringInCache(String key,String value,Integer expire);
	public String getStringInCache(String key);
	
	public int addActionUserRecord(ActionUserRecord record);
	public int deleteActionUserRecord(ActionUserRecord record);
	public int updateActionUserRecord(ActionUserRecord record);
	public List<ActionUserRecord>getActionUserRecordList(Map<String,Object>param);
	public int getActionUserRecordListCount(Map<String,Object>param);
	
	public List<Map<String,Object>>getKanJiaFriendList(Map<String,Object>param);
	
	public List<Map<String,Object>>getHouseListOfHot(Map<String,Object>param);
	public List<City>getCityListOfHot(Map<String,Object>param);
	public List<Map<String,Object>>getHouseListOfCity(Map<String,Object>param);
	public List<Map<String,Object>>getHouseListOfRecommended(Map<String,Object>param);
}
