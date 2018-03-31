package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.City;

public interface CityDao {

	public int addCity(City city);
	public int deleteCity(City city);
	public int updateCity(City city);
	public City getCity(City city);
	public List<City>getCityList(Map<String,Object>param);
	public int getCityListCount(Map<String,Object>param);
}
