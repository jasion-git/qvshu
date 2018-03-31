package com.hishu.vacation.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hishu.vacation.annotation.Log;
import com.hishu.vacation.annotation.ValidateFiled;
import com.hishu.vacation.annotation.ValidateGroup;
import com.hishu.vacation.biz.service.OrderManagementService;
import com.hishu.vacation.common.Constant;
import com.hishu.vacation.dto.AdminUser;
import com.hishu.vacation.dto.CommonParam;
import com.hishu.vacation.dto.HouseDayStatus;
import com.hishu.vacation.dto.HouseDayStatusHouse1;
import com.hishu.vacation.dto.JsonResult;
import com.hishu.vacation.dto.Order;

@Controller
@RequestMapping("/order")
public class OrderManagementController {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private OrderManagementService orderManagementService;
	
	@RequestMapping(value="/getHouseDayStatusList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseDayStatusList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>map=new HashMap<String,Object>();
		String houseIdStr=request.getParameter("houseId");
		Integer houseId=Integer.parseInt(houseIdStr);
		map.put("houseId", houseId);
		map.put("startTime", param.getStartTime());
		map.put("endTime", param.getEndTime());
		List<HouseDayStatus>list=orderManagementService.getHouseDayStatusList(map);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="houseId"),
            @ValidateFiled(index=0,notNull=true,filedName="date")
    })
	@RequestMapping(value="/getHouseDayStatusMap.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseDayStatusMap(HouseDayStatus houseDayStatus,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//获取用户信息
		AdminUser adminUser=(AdminUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		houseDayStatus.setOperatorId(adminUser.getId());
		Map<String,Object>houseDayStatusMap=orderManagementService.getHouseDayStatusMap(houseDayStatus);
		
		result.setData(houseDayStatusMap);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="houseId"),
            @ValidateFiled(index=0,notNull=true,filedName="date"),
            @ValidateFiled(index=0,notNull=true,filedName="status")
    })
	@Log
	@RequestMapping(value="/updateHouseDayStatus.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateHouseDayStatus(HouseDayStatus houseDayStatus,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//获取用户信息
		AdminUser adminUser=(AdminUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		houseDayStatus.setOperatorId(adminUser.getId());
		orderManagementService.updateHouseDayStatus(houseDayStatus);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="houseId"),
            @ValidateFiled(index=0,notNull=true,filedName="date"),
            @ValidateFiled(index=0,notNull=true,filedName="status")
    })
	@Log
	@RequestMapping(value="/addHouseDayStatus.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addHouseDayStatus(HouseDayStatus houseDayStatus,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//获取用户信息
		AdminUser adminUser=(AdminUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		houseDayStatus.setOperatorId(adminUser.getId());
		orderManagementService.addHouseDayStatus(houseDayStatus);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getHouseStatusList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseStatusList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("startTime", param.getStartTime());
		map.put("endTime", param.getEndTime());
		String cityIdStr=request.getParameter("cityId");
		if(StringUtils.isNotEmpty(cityIdStr)){
			map.put("cityId", Integer.parseInt(cityIdStr));
		}
		String districtIdStr=request.getParameter("districtId");
		if(StringUtils.isNotEmpty(districtIdStr)){
			map.put("districtId", Integer.parseInt(districtIdStr));
		}
		String likeName=request.getParameter("name");
		map.put("likeName", likeName);
		String merchantName=request.getParameter("merchantName");
		map.put("merchantName", merchantName);
		String liveCountStr=request.getParameter("liveCount");
		if(StringUtils.isNotEmpty(liveCountStr)){
			map.put("liveCount", Integer.parseInt(liveCountStr));
		}
		String roomCountStr=request.getParameter("roomCount");
		if(StringUtils.isNotEmpty(roomCountStr)){
			map.put("roomCount", Integer.parseInt(roomCountStr));
		}
		
		List<HouseDayStatusHouse1>list=orderManagementService.getHouseStatusList(map);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	//******************************订单管理 开始******************************************
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="houseType"),
            @ValidateFiled(index=0,notNull=true,filedName="bookPaymentType"),
            @ValidateFiled(index=0,notNull=true,filedName="bookGetAmount"),
            @ValidateFiled(index=0,notNull=true,filedName="bookType"),
            @ValidateFiled(index=0,notNull=true,filedName="bookAmount"),
            @ValidateFiled(index=0,notNull=true,filedName="bookLeaveTime"),
            @ValidateFiled(index=0,notNull=true,filedName="bookLiveTime"),
            @ValidateFiled(index=0,notNull=true,filedName="houseBookCount"),
            @ValidateFiled(index=0,notNull=true,filedName="userPhone"),
            @ValidateFiled(index=0,notNull=true,filedName="userName")
    })
	@Log
	@RequestMapping(value="/addOrder.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addOrder(Order order,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//获取用户信息
		AdminUser adminUser=(AdminUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		order.setOperatorId(adminUser.getId());
		orderManagementService.addOrder(order);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteOrder.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteOrder(Order order,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//获取用户信息
		AdminUser adminUser=(AdminUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		order.setOperatorId(adminUser.getId());
		orderManagementService.deleteOrder(order);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/updateOrder.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateOrder(Order order,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//获取用户信息
		AdminUser adminUser=(AdminUser) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		order.setOperatorId(adminUser.getId());
		orderManagementService.updateOrder(order);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getOrder.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getOrder(Order order,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Order orderInDB=orderManagementService.getOrder(order);
		result.setData(orderInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getOrderList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getOrderList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String[]paymentStatus=request.getParameterValues("paymentStatus[]");
		if(paymentStatus!=null&&paymentStatus.length>0){
			param.put("paymentStatusList", paymentStatus);
		}
		String[]orderStatus=request.getParameterValues("orderStatus[]");
		if(orderStatus!=null&&orderStatus.length>0){
			param.put("orderStatusList", orderStatus);
		}
		//String financialStatus=request.getParameter("financialStatus");
		//param.put("financialStatus", financialStatus);
		String[]liveStatus=request.getParameterValues("liveStatus[]");
		if(liveStatus!=null&&liveStatus.length>0){
			param.put("liveStatusList", liveStatus);
		}
		
		String orderNo=request.getParameter("orderNo");
		param.put("orderNo", orderNo);
		String operatorName=request.getParameter("operatorName");
		param.put("operatorName", operatorName);
		String userName=request.getParameter("userName");
		param.put("userName", userName);
		String userPhone=request.getParameter("userPhone");
		param.put("userPhone", userPhone);
		String houseName=request.getParameter("houseName");
		param.put("houseName", houseName);
		String districtName=request.getParameter("districtName");
		param.put("districtName", districtName);
		String merchantName=request.getParameter("merchantName");
		param.put("merchantName", merchantName);
		String bookTime=request.getParameter("bookTime");
		param.put("bookTime", bookTime);
		String bookLiveTime=request.getParameter("bookLiveTime");
		param.put("bookLiveTime", bookLiveTime);
		
		List<Map<String,Object>>list=orderManagementService.getOrderList(param);
		int total=orderManagementService.getOrderListCount(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	//******************************订单管理 介绍******************************************
}
