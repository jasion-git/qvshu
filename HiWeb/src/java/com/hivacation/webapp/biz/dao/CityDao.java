package com.hivacation.webapp.biz.dao;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.District;

public interface CityDao {

	public List<City>getCityListOfHot(Map<String,Object>param);
	
	public List<District>getDistrictListOfCity(City city);
	
	public District getDistrictDetail(District district);
	
	public List<Map<String,Object>>getCityDistrictList(Map<String,Object>param);
}
