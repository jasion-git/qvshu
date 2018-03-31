package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.HouseDayStatus;
import com.hishu.vacation.dto.HouseDayStatusHouse1;

public interface HouseDayStatusDao {

	public int addHouseDayStatus(HouseDayStatus houseDayStatus);
	public int deleteHouseDayStatus(HouseDayStatus houseDayStatus);
	public int deleteHouseDayStatusAfterHalfYear(HouseDayStatus houseDayStatus);
	public int updateHouseDayStatus(HouseDayStatus houseDayStatus);
	public HouseDayStatus getHouseDayStatus(HouseDayStatus houseDayStatus);
	public Map<String,Object> getHouseDayStatusMap(HouseDayStatus houseDayStatus);
	public List<HouseDayStatus>getHouseDayStatusList(Map<String,Object>param);
	public int getHouseDayStatusListCount(Map<String,Object>param);
	
	public List<HouseDayStatusHouse1>getHouseStatusList(Map<String,Object>param);
	
	public Map<String,Object>getHouseStatus(Map<String,Object>param);
	
	public List<HouseDayStatus> getHouseDayStatusForLock(Map<String,Object>param);
	
	public int unbindHouseDayStatusOfOrder(HouseDayStatus houseDayStatus);
}
