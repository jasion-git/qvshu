package com.hishu.vacation.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hishu.vacation.biz.dao.HouseDayStatusDao;
import com.hishu.vacation.biz.dao.OrderDao;
import com.hishu.vacation.biz.service.OrderManagementService;
import com.hishu.vacation.dto.HouseDayStatus;
import com.hishu.vacation.dto.HouseDayStatusHouse1;
import com.hishu.vacation.dto.Order;
import com.hishu.vacation.dto.OrderLog;
import com.hishu.vacation.exception.BizException;
import com.hishu.vacation.exception.ReturnCode;

@Service
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private HouseDayStatusDao houseDayStatusDao;
	@Autowired
	private OrderDao orderDao;
	
	
	public static void main(String[]args)throws Exception{
		OrderManagementServiceImpl t=new OrderManagementServiceImpl();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime=df.parse("2018-02-01 00:00:00");
		Date endTime=df.parse("2018-02-05 00:00:00");
		
		List<String>list=t.genDateList(startTime, endTime);
		List<String>list2=new ArrayList<String>();
		list2.addAll(list);
		list.remove(0);
		System.out.println(list);
		System.out.println(list2);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouseDayStatus(HouseDayStatus houseDayStatus) {
		return houseDayStatusDao.addHouseDayStatus(houseDayStatus);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseDayStatus(HouseDayStatus houseDayStatus) {
		return houseDayStatusDao.deleteHouseDayStatus(houseDayStatus);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseDayStatusAfterHalfYear(HouseDayStatus houseDayStatus) {
		return houseDayStatusDao.deleteHouseDayStatusAfterHalfYear(houseDayStatus);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouseDayStatus(HouseDayStatus houseDayStatus)throws Exception {
		//标记有房时，先查询是否有订单关联，如果有自动单，则提示不能修改，如果是手动单则提示先修改手动单信息后，再来标记
		HouseDayStatus houseDayStatusInDB=houseDayStatusDao.getHouseDayStatus(houseDayStatus);
		if(houseDayStatus.getStatus()!=null
				&&houseDayStatus.getStatus().compareTo(houseDayStatusInDB.getStatus())!=0
				&&houseDayStatus.getStatus().intValue()==1){
			//改了房态，且是标记为有房状态，则查询是否有订单关联
			Integer orderId=houseDayStatusInDB.getOrderId();
			if(orderId!=null){
				//有订单关联
				Order order=new Order();
				order.setId(orderId);
				order=orderDao.getOrder(order);
				throw new BizException(ReturnCode.ERROR_HOUSE_STATUS_REF_ORDER,"房态已经关联了订单"+order.getOrderNo()+"，请先取消订单");
			}
		}
		houseDayStatus.setId(houseDayStatusInDB.getId());
		return houseDayStatusDao.updateHouseDayStatus(houseDayStatus);
	}

	@Override
	public HouseDayStatus getHouseDayStatus(HouseDayStatus houseDayStatus) {
		return houseDayStatusDao.getHouseDayStatus(houseDayStatus);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String,Object>getHouseDayStatusMap(HouseDayStatus houseDayStatus){
		//先获取，如果没有，则新增
		Map<String,Object>houseDayStatusMap=houseDayStatusDao.getHouseDayStatusMap(houseDayStatus);
		if(houseDayStatusMap==null||houseDayStatusMap.isEmpty()){
			//没有，则新增
			houseDayStatus.setStatus(0);
			houseDayStatusDao.addHouseDayStatus(houseDayStatus);
			//再获取一次
			houseDayStatusMap=houseDayStatusDao.getHouseDayStatusMap(houseDayStatus);
		}
		
		return houseDayStatusMap;
	}

	@Override
	public List<HouseDayStatus> getHouseDayStatusList(Map<String, Object> param) {
		return houseDayStatusDao.getHouseDayStatusList(param);
	}

	@Override
	public int getHouseDayStatusListCount(Map<String, Object> param) {
		return houseDayStatusDao.getHouseDayStatusListCount(param);
	}

	@Override
	public List<HouseDayStatusHouse1> getHouseStatusList(
			Map<String, Object> param) {
		return houseDayStatusDao.getHouseStatusList(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addOrder(Order order) throws Exception{
		if(Order.HOUSE_TYPE_SYSTEM.equals(order.getHouseType())){
			//系统房源，则要判断房源Id是否为空
			if(order.getHouseId()==null){
				throw new BizException(ReturnCode.ERROR_PARAM_IS_REQUIRED,"houseId");
			}
		}else{
			String houseDistrict=order.getHouseDistrict();
			if(StringUtils.isEmpty(houseDistrict)){
				throw new BizException(ReturnCode.ERROR_PARAM_IS_REQUIRED,"houseDistrict");
			}
			String houseName=order.getHouseName();
			if(StringUtils.isEmpty(houseName)){
				throw new BizException(ReturnCode.ERROR_PARAM_IS_REQUIRED,"houseName");
			}
			//系统外的房源，则不需要控制房态了，直接新增订单
			int c=_addOrder(order);
			return c;
		}
		
		
		//先根据入住时间和退房时间，生成所有关联的天
		//1.先根据订单入住信息和房源信息，判断是否还有房（行锁）
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("houseId", order.getHouseId());
		Date liveTime=order.getBookLiveTime();
		param.put("startTime", liveTime);
		Date leaveTime=order.getBookLeaveTime();
		param.put("endTime", leaveTime);
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<String>dates=genDateList(liveTime, leaveTime);
		List<String>datesClone=new ArrayList<String>();
		datesClone.addAll(dates);
		List<HouseDayStatus>list=houseDayStatusDao.getHouseDayStatusForLock(param);
		//2.无房，返回
		
		if(list==null||list.isEmpty()){
			//throw new BizException(ReturnCode.ERROR_NO_HOUSE_STATUS, df.format(liveTime)+","+df.format(leaveTime));
		}
		int i=0;
		StringBuffer sb=new StringBuffer();
		for(HouseDayStatus ds:list){
			if(ds.getStatus()!=null&&ds.getStatus().intValue()!=1){
				//有没有房子的房态
				if(i==0){
					sb.append(df.format(ds.getDate()));
					i++;
				}else{
					sb.append(",").append(df.format(ds.getDate()));
				}
			}
			String temp=df.format(ds.getDate());
			if(dates.contains(temp)){
				dates.remove(temp);
			}
		}
		//补回空缺的房态
		if(!dates.isEmpty()){
			for(String date:dates){
				HouseDayStatus houseDayStatus=new HouseDayStatus();
				houseDayStatus.setHouseId(order.getHouseId());
				houseDayStatus.setDate(df.parse(date));
				houseDayStatus.setStatus(1);
				houseDayStatus.setOperatorId(order.getOperatorId());
				houseDayStatusDao.addHouseDayStatus(houseDayStatus);
				
				list.add(houseDayStatus);//把新加的房态加进去
			}
		}
		
		if(StringUtils.isNotEmpty(sb.toString())){
			throw new BizException(ReturnCode.ERROR_NO_HOUSE_STATUS, sb.toString());
		}
		
		//4.生成订单（如果是手动单，则订单状态=正常，付款状态=支付成功，如果是自动下单，则订单状态=正常，付款状态=未支付）
		
		/*
		 付款状态（0-未支付，1-支付超时，2-支付失败，3-支付完成-确认中，4-支付成功）
		订单状态（0-有效，1-预定失败，2-已取消，3-等待退款，4-退款失败，5-已删除）
		入住状态(0-待入住，1-已入住，2-已退房)
		财务状态（0-未处理，已处理）
		 * */
		int c=_addOrder(order);
		
		//3.标记无房，锁住房态，且关联订单
		for(HouseDayStatus ds:list){
			ds.setStatus(0);//设置房态无房状态
			ds.setOrderId(order.getId());
			ds.setOperatorId(order.getOperatorId());
			houseDayStatusDao.updateHouseDayStatus(ds);
		}
		return c; 
	}

	private int _addOrder(Order order){
		String orderNo=UUID.randomUUID().toString().replace("-", "");
		order.setOrderNo(orderNo);
		order.setPaymentStatus(4);
		order.setOrderStatus(0);
		order.setLiveStatus(Order.LIVE_STATUS_WAIT_FOR_LIVE);//入住状态（0-待入住，1-已入住，2-已退房）
		order.setFinancialStatus(Order.FINANCIAL_STATUS_NO_HANDL);//财务状态（0-未处理，已处理）
		Date now=new Date();
		order.setBookTime(now);
		order.setType("manual");//订单类型（wechat-系统订单，zhifubao-支付宝，manual-手动录入，other-其他）
		int c=orderDao.addOrder(order);
		//插入订单日志
		OrderLog log=new OrderLog();
		log.setOrderId(order.getId());
		log.setAction("新增订单");
		log.setResult("成功");
		log.setOperatorId(order.getOperatorId()!=null?order.getOperatorId():0);
		orderDao.addOrderLog(log);
		
		return c;
	}
	
	private List<String>genDateList(Date startTime,Date endTime){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		c.setTime(startTime);
		
		String eTime=df.format(endTime);
		List<String>list=new ArrayList<String>();
		while(true){
			String time=df.format(c.getTime());
			if(time.equals(eTime)){
				list.add(time);
				break;
			}
			list.add(time);
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		return list;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteOrder(Order order) throws Exception{
		//已付款订单不能删除
		Order orderInDB=orderDao.getOrder(order);
		if(order.getPaymentStatus().intValue()==4){
			throw new BizException(ReturnCode.ERROR_ORDER_CAN_NOT_DELETE_FOR_PAID,"已付款订单不能删除");
		}
		//当前时间还在入住时间和退房时间范围内，不能删除
		Date now=new Date();
		if(now.before(orderInDB.getBookLeaveTime())&&orderInDB.getOrderStatus().intValue()!=2){
			throw new BizException(ReturnCode.ERROR_ORDER_CAN_NOT_DELETE_FOR_LEAVE_TIME,"订单还在退房期限内，不能删除");
		}
		
		int c=orderDao.deleteOrder(order);
		if(c>0){
			//插入订单日志
			OrderLog log=new OrderLog();
			log.setOrderId(order.getId());
			log.setAction("删除订单");
			log.setResult("成功");
			log.setOperatorId(order.getOperatorId()!=null?order.getOperatorId():0);
			orderDao.addOrderLog(log);
		}
		
		return c;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateOrder(Order order) throws Exception{
		Order orderInDB=orderDao.getOrder(order);
		
		if("manual".equals(orderInDB.getType())){
			//手动单，可以更新订单状态，付款状态，用户信息，金额信息，入住信息
			if(order.getOrderStatus()!=null&&order.getOrderStatus().intValue()==Order.ORDER_STATUS_DELETE){
				//不允许更新的接口做删除
				order.setOrderStatus(null);
			}
			//如果是取消订单，则需要清空房态的订单Id关联
			if(order.getOrderStatus()!=null&&order.getOrderStatus().intValue()==Order.ORDER_STATUS_CANCEL){
				HouseDayStatus houseDayStatus=new HouseDayStatus();
				houseDayStatus.setOrderId(order.getId());
				houseDayStatusDao.unbindHouseDayStatusOfOrder(houseDayStatus);
			}
			int c=orderDao.updateOrder(order);
			if(c>0){
				//插入订单日志
				OrderLog log=new OrderLog();
				log.setOrderId(order.getId());
				log.setAction("修改订单");
				log.setResult("成功");
				log.setOperatorId(order.getOperatorId()!=null?order.getOperatorId():0);
				orderDao.addOrderLog(log);
			}
			return c;
		}else{
			//如果是自动单，不能更新金额，用户信息，只能更新付款状态，订单状态（不能删除）
			Order updateOrder=new Order();
			updateOrder.setId(order.getId());
			updateOrder.setPaymentStatus(order.getPaymentStatus());
			if(order.getOrderStatus()!=null&&order.getOrderStatus().intValue()==Order.ORDER_STATUS_DELETE){
				//不允许更新的接口做删除
				order.setOrderStatus(null);
			}
			updateOrder.setOrderStatus(order.getOrderStatus());
			int c=orderDao.updateOrder(updateOrder);
			if(c>0){
				//插入订单日志
				OrderLog log=new OrderLog();
				log.setOrderId(order.getId());
				log.setAction("修改订单");
				log.setResult("成功");
				log.setOperatorId(order.getOperatorId()!=null?order.getOperatorId():0);
				orderDao.addOrderLog(log);
			}
			return c;
		}
	}

	@Override
	public Order getOrder(Order order) {
		return orderDao.getOrder(order);
	}

	@Override
	public List<Map<String, Object>> getOrderList(Map<String, Object> param) {
		return orderDao.getOrderList(param);
	}

	@Override
	public int getOrderListCount(Map<String, Object> param) {
		return orderDao.getOrderListCount(param);
	}

}
