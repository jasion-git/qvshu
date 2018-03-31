package com.hivacation.webapp.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hivacation.webapp.biz.dao.OrderDao;
import com.hivacation.webapp.biz.service.OrderService;
import com.hivacation.webapp.dto.HouseStatus;
import com.hivacation.webapp.dto.Order;
import com.hivacation.webapp.dto.OrderLog;
import com.hivacation.webapp.dto.OrderThirdPartyProduct;
import com.hivacation.webapp.dto.ThirdPartyProduct;
import com.hivacation.webapp.exception.BizException;
import com.hivacation.webapp.exception.ReturnCode;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public List<HouseStatus> getHouseStatusList(Map<String, Object> param) {
		return orderDao.getHouseStatusListForUpdate(param);
	}

	@Override
	public List<ThirdPartyProduct> getThirdPartyProductList(
			Map<String, Object> param) {
		return orderDao.getThirdPartyProductList(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int createOrder(Order order) throws Exception{
		/*
		 * 根据时间和房源Id查询是否还有房态
		 * 如果买了小区第三方产品
		 * 对比金额
		 * 如果正确，则锁住房态，
		 * 新建订单
		 * 
		 * 
		 * */
		Double thirdPartyProductAmount=0.0;

		List<OrderThirdPartyProduct>orderThirdPartyProductList=order.getOrderThirdPartyProductList();
		if(orderThirdPartyProductList!=null&&!orderThirdPartyProductList.isEmpty()){
			//还附加买了第三方产品,则要计算价格
			List<Integer>ids=new ArrayList<Integer>();
			Map<Integer,String>nameMap=new HashMap<Integer,String>();
			Map<Integer,Double>packagePriceMap=new HashMap<Integer,Double>();
			Map<Integer,Double>singlePriceMap=new HashMap<Integer,Double>();
			Map<Integer,Integer>map=new HashMap<Integer,Integer>();
			for(OrderThirdPartyProduct p:orderThirdPartyProductList){
				ids.add(p.getProductId());
				map.put(p.getProductId(), p.getCount());
			}
			Map<String,Object>para=new HashMap<String,Object>();
			para.put("ids", ids);
			para.put("houseId", order.getHouseId());
			List<ThirdPartyProduct>list=orderDao.getThirdPartyProductList(para);
			if(orderThirdPartyProductList.size()>list.size()){
				throw new BizException(ReturnCode.ERROR_PAY_THIRD_PARTY_PRODUCT_IS_NOT_EXIST,"购买的第三方产品不存在");
			}
			//计算价格
			for(ThirdPartyProduct tp:list){
				//还要加上数量
				thirdPartyProductAmount+=(tp.getSinglePrice()*map.get(tp.getId()));
				
				nameMap.put(tp.getId(), tp.getName());
				packagePriceMap.put(tp.getId(), tp.getPackagePrice());
				singlePriceMap.put(tp.getId(), tp.getSinglePrice());
			}
			//补回名称，单价，团价，数量
			for(OrderThirdPartyProduct op:orderThirdPartyProductList){
				op.setName(nameMap.get(op.getProductId())==null?"产品1":nameMap.get(op.getProductId()));
				op.setPackagePrice(packagePriceMap.get(op.getProductId())==null?0:packagePriceMap.get(op.getProductId()));
				op.setSinglePrice(singlePriceMap.get(op.getProductId())==null?0:singlePriceMap.get(op.getProductId()));
			}
		}
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("houseId", order.getHouseId());
		param.put("startTime", order.getBookLiveTime());
		param.put("endTime", order.getBookLeaveTime());
		List<HouseStatus>houseStatusList=orderDao.getHouseStatusListForUpdate(param);
		if(houseStatusList==null||houseStatusList.isEmpty()){
			throw new BizException(ReturnCode.ERROR_HOUSE_STATUS_EMPTY,"房态信息为空");
		}
		Double orderAmount=0.0;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object>dates=new HashMap<String,Object>();
		for(HouseStatus houseStatus:houseStatusList){
			if(houseStatus.getStatus()!=1){
				throw new BizException(ReturnCode.ERROR_HOUSE_STATUS_HAD_BOOK,"房源已经被预定");
			}
			dates.put(df.format(houseStatus.getDate()), 1);//状态每天
		}
		
		//循环查询每天房价
		List<HouseStatus>houseDatePriceList=orderDao.getHouseDatePriceList(param);
		for(HouseStatus datePrice:houseDatePriceList){
			if(datePrice.getOrderAmount()==null){
				throw new BizException(ReturnCode.ERROR_HOUSE_STATUS_ORDER_AMOUNT_MISSING,"房态价格缺失1");
			}
			dates.remove(df.format(datePrice.getDate()));
			orderAmount+=datePrice.getOrderAmount();
		}
		if(!dates.isEmpty()){
			throw new BizException(ReturnCode.ERROR_HOUSE_STATUS_ORDER_AMOUNT_MISSING,"房态价格缺失2");
		}
		
		//两个金额相加，如果和订单金额不相等，则认为是篡改了金额，新建订单不成功
		Double total=thirdPartyProductAmount+orderAmount;
		
		if(total.compareTo(order.getBookGetAmount())!=0){
			throw new BizException(ReturnCode.ERROR_ORDER_AMOUNT_NOT_EQUAL,"订单金额错误");
		}
		//生成订单
		int c=_addOrder(order);
		//修改房态
		Map<String,Object>pp=new HashMap<String,Object>();
		pp.put("orderId", order.getId());
		pp.put("houseStatusList", houseStatusList);
		orderDao.updateHouseStatusForOrder(pp);
		
		return c;
	}
	
	private int _addOrder(Order order){
		String orderNo=UUID.randomUUID().toString().replace("-", "");
		order.setOrderNo(orderNo);
		order.setPaymentStatus(4);
		order.setOrderStatus(0);
		order.setLiveStatus(Order.LIVE_STATUS_WAIT_FOR_LIVE);//入住状态（0-待入住，1-已入住，2-已退房）
		order.setFinancialStatus(Order.FINANCIAL_STATUS_NO_HANDL);//财务状态（0-未处理，已处理）
		order.setHouseType("system");//房源类型（system-系统房源，other-系统外房源）
		order.setBookPaymentType(2);//收款方式(1-现金，2-在线付款，3-其他)
		order.setBookType(1);//预定类型（1-收全款，2-收定金）
		Date now=new Date();
		order.setBookTime(now);
		order.setType("wechat");//订单类型（wechat-系统订单，zhifubao-支付宝，manual-手动录入，other-其他）
		int c=orderDao.addOrder(order);
		
		//以下的订单附属数据可以通过子线程产生，因为订单生产后，需要尽快返回
		OrderTask task=new OrderTask();
		task.setOrder(order);
		task.start();
		
		return c;
	}
	
	class OrderTask extends Thread {
		private Order order;
		public void setOrder(Order order){
			this.order=order;
		}
		public void run(){
			//插入订单日志
			OrderLog log=new OrderLog();
			log.setOrderId(order.getId());
			log.setAction("新增订单");
			log.setResult("成功");
			log.setOperatorId(order.getOperatorId()!=null?order.getOperatorId():0);
			orderDao.addOrderLog(log);
			
			//插入第三方产品
			if(order.getOrderThirdPartyProductList()!=null&&!order.getOrderThirdPartyProductList().isEmpty()){
				Map<String,Object>pp=new HashMap<String,Object>();
				pp.put("orderId", order.getId());
				pp.put("productList", order.getOrderThirdPartyProductList());
				orderDao.addOrderThirdPartyProductList(pp);
			}
		}
	}


}
