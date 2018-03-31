package com.hivacation.webapp.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hivacation.webapp.annotation.ValidateFiled;
import com.hivacation.webapp.annotation.ValidateGroup;
import com.hivacation.webapp.biz.service.OrderService;
import com.hivacation.webapp.biz.service.UserService;
import com.hivacation.webapp.dto.JsonResult;
import com.hivacation.webapp.dto.Order;
import com.hivacation.webapp.dto.OrderThirdPartyProduct;
import com.hivacation.webapp.dto.ThirdPartyProduct;
import com.hivacation.webapp.dto.UserInfo;
import com.hivacation.webapp.exception.BizException;
import com.hivacation.webapp.exception.ReturnCode;

@Controller
@RequestMapping("/order")
public class OrderController {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId"),
            @ValidateFiled(index=0,notNull=true,filedName="userName"),
            @ValidateFiled(index=0,notNull=true,filedName="userPhone")
    })
	@RequestMapping(value="/createUser.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult createUser(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String openId=request.getParameter("openId");
		log.info("openId="+openId);
		String userName=request.getParameter("userName");
		log.info("userName="+userName);
		String userPhone=request.getParameter("userPhone");
		log.info("userPhone="+userPhone);
		
		Integer userId=null;
		//查询用户是否存在，不存在用户，则新增用户
		UserInfo user=new UserInfo();
		user.setWxOpenId(openId);
		user=userService.getUser(user);
		if(user==null){
			//再用手机号查询用户
			UserInfo user2=new UserInfo();
			user2.setPhone(userPhone);
			user2=userService.getUser(user2);
			if(user2==null){
				//新增用户
				UserInfo addUser=new UserInfo();
				addUser.setName(userName);
				addUser.setAccount(userPhone);
				addUser.setPhone(userPhone);
				addUser.setPassword("123456789");
				addUser.setWxOpenId(openId);
				userService.addUser(addUser);
				userId=addUser.getId();
			}else{
				//把openId更新回去
				userId=user2.getId();
				UserInfo updateUser=new UserInfo();
				updateUser.setId(userId);
				updateUser.setWxOpenId(openId);
				userService.updateUser(updateUser);
			}
		}else{
			//已经存在用户
			//把手机号更新
			userId=user.getId();
			UserInfo updateUser=new UserInfo();
			updateUser.setId(userId);
			updateUser.setPhone(userPhone);
			userService.updateUser(updateUser);
		}
		if(userId==null){
			throw new BizException(ReturnCode.ERROR_CREATE_ORDER_REF_USER_FAIL,"生成用户失败");
		}
		
		UserInfo user2=new UserInfo();
		user2.setId(userId);
		result.setData(user2);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId"),
            @ValidateFiled(index=0,notNull=true,filedName="userId"),
            @ValidateFiled(index=0,notNull=true,filedName="houseId"),
            @ValidateFiled(index=0,notNull=true,filedName="liveDate"),
            @ValidateFiled(index=0,notNull=true,filedName="leaveDate"),
            @ValidateFiled(index=0,notNull=true,filedName="userName"),
            @ValidateFiled(index=0,notNull=true,filedName="userPhone"),
            @ValidateFiled(index=0,notNull=true,filedName="orderAmount")
    })
	@RequestMapping(value="/bookHouse.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult bookHouse(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String openId=request.getParameter("openId");
		log.info("openId="+openId);
		String houseIdStr=request.getParameter("houseId");
		Integer houseId=Integer.parseInt(houseIdStr);
		log.info("houseId="+houseId);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String liveDateStr=request.getParameter("liveDate");
		Date liveDate=df.parse(liveDateStr);
		log.info("liveDate="+liveDate);
		String leaveDateStr=request.getParameter("leaveDate");
		Date leaveDate=df.parse(leaveDateStr);
		log.info("leaveDate="+leaveDate);
		String userName=request.getParameter("userName");
		log.info("userName="+userName);
		String userPhone=request.getParameter("userPhone");
		log.info("userPhone="+userPhone);
		String orderAmountStr=request.getParameter("orderAmount");
		Double orderAmount=Double.parseDouble(orderAmountStr);
		
		String userIdStr=request.getParameter("userId");
		Integer userId=Integer.parseInt(userIdStr);
		
		Order order=new Order();
		order.setHouseId(houseId);
		order.setBookLiveTime(liveDate);
		order.setBookLeaveTime(leaveDate);
		order.setBookGetAmount(orderAmount);
		order.setBookAmount(orderAmount);//订单总金额
		order.setHouseBookCount(1);//预定套数
		
		String[]thirdPartyProductList=request.getParameterValues("thirdPartyProduct[]");
		if(thirdPartyProductList!=null&&thirdPartyProductList.length>0){
			List<OrderThirdPartyProduct>orderThirdPartyProductList=new ArrayList<OrderThirdPartyProduct>();
			order.setOrderThirdPartyProductList(orderThirdPartyProductList);
			for(String s:thirdPartyProductList){
				String[]items=s.split("-");
				if(items==null||items.length!=2){
					continue;
				}
				log.info("productId="+items[0]);
				log.info("productCount="+items[1]);
				OrderThirdPartyProduct p=new OrderThirdPartyProduct();
				p.setProductId(Integer.parseInt(items[0]));
				p.setCount(Integer.parseInt(items[1]));
				orderThirdPartyProductList.add(p);
			}
		}
		
		//新增订单
		if(userId==null){
			throw new BizException(ReturnCode.ERROR_CREATE_ORDER_REF_USER_FAIL,"订单关联用户失败");
		}
		order.setUserId(userId);
		//TODO 如果此单的用户是由代理人开发出来的，则订单加上代理人Id，代理人有两级（最多两级）
		//查询用户信息
		UserInfo user=new UserInfo();
		user.setId(userId);
		user=userService.getUser(user);
		if(user==null){
			throw new BizException(ReturnCode.ERROR_CREATE_ORDER_REF_USER_FAIL,"订单关联用户失败");
		}
		if(user.getProxyUser1Id()!=null){
			order.setProxyUser1Id(user.getProxyUser1Id());
			if((user.getProxyUser2Id()!=null)){
				order.setProxyUser2Id(user.getProxyUser2Id());
			}
		}
		
		orderService.createOrder(order);
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	
}
