package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.HouseImg;

public interface HouseImgDao {

	public int addHouseImg(HouseImg img);
	public int deleteHouseImg(HouseImg img);
	public int deleteHouseImgGroup(HouseImg img);
	public int updateHouseImg(HouseImg img);
	public HouseImg getHouseImg(HouseImg img);
	public List<HouseImg>getHouseImgList(Map<String,Object>param);
	public int getHouseImgListCount(Map<String,Object>param);
	
}
