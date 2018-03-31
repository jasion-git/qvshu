package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.ThirdPartRoomStatus;

public interface ThirdPartRoomStatusDao {

	public int addThirdPartRoomStatus(ThirdPartRoomStatus roomStatus);
	public int deleteThirdPartRoomStatus(ThirdPartRoomStatus roomStatus);
	public int updateThirdPartRoomStatus(ThirdPartRoomStatus roomStatus);
	public ThirdPartRoomStatus getThirdPartRoomStatus(ThirdPartRoomStatus roomStatus);
	public List<ThirdPartRoomStatus>getThirdPartRoomStatusList(Map<String,Object>param);
	public int getThirdPartRoomStatusListCount(Map<String,Object>param);
	
	public List<Map<String,Object>>getThirdPartHouseList(Map<String,Object>param);
}
