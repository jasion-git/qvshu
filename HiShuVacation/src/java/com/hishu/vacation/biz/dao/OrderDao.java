package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Order;
import com.hishu.vacation.dto.OrderLog;

public interface OrderDao {

	public int addOrder(Order order);
	public int deleteOrder(Order order);
	public int updateOrder(Order order);
	public Order getOrder(Order order);
	public List<Map<String,Object>>getOrderList(Map<String,Object>param);
	public int getOrderListCount(Map<String,Object>param);
	
	public int addOrderLog(OrderLog log);
	public List<OrderLog>getOrderLogList(OrderLog log);
}
