package com.hivacation.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.District;
import com.hivacation.webapp.dto.House;
import com.hivacation.webapp.dto.ThirdPartyProduct;

public interface HouseService {

	public House getHouse(House house,boolean loadDetail);
	
	public List<Map<String,Object>>getSimpleHouseList(Map<String,Object>param);
	
	public List<District>getDistrictListOfCity(City city);
	
	public District getDistrictDetail(District district);
	
	public List<Map<String,Object>>getCityDistrictList(Map<String,Object>param);
	public List<Map<String,Object>>getHouseStatusList(Map<String,Object>param);
	/**
	 * 查询小区第三方产品信息
	 * @param param
	 * @return
	 */
	public List<ThirdPartyProduct>getThirdPartyProductList(Map<String,Object>param);
}
