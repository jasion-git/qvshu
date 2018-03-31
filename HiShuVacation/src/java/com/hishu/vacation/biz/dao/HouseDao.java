package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.House;
import com.hishu.vacation.dto.HouseAttribute;
import com.hishu.vacation.dto.HouseFacility;
import com.hishu.vacation.dto.HouseReception;

public interface HouseDao {

	public int addHouse(House house);
	public int deleteHouse(House house);
	public int updateHouse(House house);
	public House getHouse(House house);
	public List<House>getHouseList(Map<String,Object>param);
	public int getHouseListCount(Map<String,Object>param);
	
	public int addHouseAttribute(HouseAttribute houseAttribute);
	public int deleteHouseAttribute(HouseAttribute houseAttribute);
	public int updateHouseAttribute(HouseAttribute houseAttribute);
	public HouseAttribute getHouseAttribute(HouseAttribute houseAttribute);
	public List<HouseAttribute>getHouseAttributeList(Map<String,Object>param);
	public int getHouseAttributeListCount(Map<String,Object>param);
	public List<HouseAttribute>getHouseAttributeListOfHouse(Map<String,Object>param);
	public int batchAddHouseAttributeRef(Map<String,Object>param);
	public int bathDeleteHouseAttributeRef(Map<String,Object>param);
	
	public int addHouseFacility(HouseFacility houseFacility);
	public int deleteHouseFacility(HouseFacility houseFacility);
	public int updateHouseFacility(HouseFacility houseFacility);
	public HouseFacility getHouseFacility(HouseFacility houseFacility);
	public List<HouseFacility>getHouseFacilityList(Map<String,Object>param);
	public int getHouseFacilityListCount(Map<String,Object>param);
	public List<HouseFacility>getHouseFacilityOfHouse(Map<String,Object>param);
	public int bathAddHouseFacilityRef(Map<String,Object>param);
	public int bathDeleteHouseFacilityRef(Map<String,Object>param);
	
	public int addHouseReception(HouseReception houseReceptioin);
	public int deleteHouseReception(HouseReception houseReceptioin);
	public int updateHouseReception(HouseReception houseReceptioin);
	public HouseReception getHouseReception(HouseReception houseReceptioin);
	public List<HouseReception>getHouseReceptionList(Map<String,Object>param);
	public int getHouseReceptionListCount(Map<String,Object>param);
	public List<HouseReception>getHouseReceptionOfHouse(Map<String,Object>param);
	public int bathAddHouseReceptionRef(Map<String,Object>param);
	public int bathDeleteHouseReceptionRef(Map<String,Object>param);
	
	public List<Map<String,Object>>getHouseListOfBeOverdue(Map<String,Object>param);
	public List<Map<String,Object>>getHouseListOfWillBeOverdue(Map<String,Object>param);
}
