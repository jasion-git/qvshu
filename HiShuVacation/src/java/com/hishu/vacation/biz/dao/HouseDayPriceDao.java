package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.HouseDayPrice;

public interface HouseDayPriceDao {

	public int addHouseDayPrice(HouseDayPrice houseDayPrice);
	public int deleteHouseDayPrice(HouseDayPrice houseDayPrice);
	public int updateHouseDayPrice(HouseDayPrice houseDayPrice);
	public HouseDayPrice getHouseDayPrice(HouseDayPrice houseDayPrice);
	public List<HouseDayPrice>getHouseDayPriceList(Map<String,Object>param);
	public int getHouseDayPriceListCount(Map<String,Object>param);
}
