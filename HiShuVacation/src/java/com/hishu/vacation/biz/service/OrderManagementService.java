package com.hishu.vacation.biz.service;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.HouseDayStatus;
import com.hishu.vacation.dto.HouseDayStatusHouse1;
import com.hishu.vacation.dto.Order;

public interface OrderManagementService {

	public int addHouseDayStatus(HouseDayStatus houseDayStatus);
	public int deleteHouseDayStatus(HouseDayStatus houseDayStatus);
	public int deleteHouseDayStatusAfterHalfYear(HouseDayStatus houseDayStatus);
	public int updateHouseDayStatus(HouseDayStatus houseDayStatus)throws Exception;
	public HouseDayStatus getHouseDayStatus(HouseDayStatus houseDayStatus);
	public Map<String,Object>getHouseDayStatusMap(HouseDayStatus houseDayStatus);
	public List<HouseDayStatus>getHouseDayStatusList(Map<String,Object>param);
	public int getHouseDayStatusListCount(Map<String,Object>param);
	public List<HouseDayStatusHouse1>getHouseStatusList(Map<String,Object>param);
	
	public int addOrder(Order order) throws Exception;
	public int deleteOrder(Order order) throws Exception;
	public int updateOrder(Order order) throws Exception;
	public Order getOrder(Order order);
	public List<Map<String,Object>>getOrderList(Map<String,Object>param);
	public int getOrderListCount(Map<String,Object>param);
}
