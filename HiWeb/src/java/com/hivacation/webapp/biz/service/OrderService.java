package com.hivacation.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.HouseStatus;
import com.hivacation.webapp.dto.Order;
import com.hivacation.webapp.dto.ThirdPartyProduct;

public interface OrderService {

	public List<HouseStatus>getHouseStatusList(Map<String,Object>param);
	
	public List<ThirdPartyProduct>getThirdPartyProductList(Map<String,Object>param);
	
	public int createOrder(Order order)throws Exception;
}
