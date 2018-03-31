package com.hivacation.webapp.biz.dao;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.HouseStatus;
import com.hivacation.webapp.dto.Order;
import com.hivacation.webapp.dto.OrderLog;
import com.hivacation.webapp.dto.OrderThirdPartyProduct;
import com.hivacation.webapp.dto.ThirdPartyProduct;

public interface OrderDao {

	public List<HouseStatus>getHouseStatusListForUpdate(Map<String,Object>param);
	public int updateHouseStatusForOrder(Map<String,Object>param);
	public List<HouseStatus>getHouseDatePriceList(Map<String,Object>param);
	
	public List<ThirdPartyProduct>getThirdPartyProductList(Map<String,Object>param);
	
	public int addOrder(Order order);
	public int addOrderLog(OrderLog log);
	public int addOrderThirdPartyProductList(Map<String,Object>param);
}
