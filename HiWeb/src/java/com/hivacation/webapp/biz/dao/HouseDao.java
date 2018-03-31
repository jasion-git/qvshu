package com.hivacation.webapp.biz.dao;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.Comment;
import com.hivacation.webapp.dto.DistrictImg;
import com.hivacation.webapp.dto.House;
import com.hivacation.webapp.dto.HouseAttribute;
import com.hivacation.webapp.dto.HouseFacility;
import com.hivacation.webapp.dto.HouseImg;
import com.hivacation.webapp.dto.HouseReception;

public interface HouseDao {

	public int addHouse(House house);
	public int deleteHouse(House house);
	public int updateHouse(House house);
	public House getHouse(House house);
	public List<House>getHouseList(Map<String,Object>param);
	public int getHouseListCount(Map<String,Object>param);
	
	public List<HouseImg>getHouseImgs(Map<String,Object>param);
	public List<HouseAttribute>getHouseAttributes(Map<String,Object>param);
	
	/**
	 * 获取热门房源
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>>getHouseListOfHot(Map<String,Object>param);
	
	public List<Map<String,Object>>getHouseListOfCity(Map<String,Object>param);
	
	public List<Map<String,Object>>getHouseListOfRecommended(Map<String,Object>param);
	
	public List<HouseImg>getHouseImgList(House house);
	public List<HouseFacility>getHouseFacilityList(House house);
	public List<DistrictImg>getDistrictImgList(House house);
	public List<HouseReception>getHouseReceptionList(House house);
	public List<Comment>getCommentList(House house);
	
	public List<Map<String,Object>>getSimpleHouseList(Map<String,Object>param);
	
	public List<Map<String,Object>>getHouseStatusList(Map<String,Object>param);
}
