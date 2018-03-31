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
import com.hishu.vacation.biz.service.OperationService;
import com.hishu.vacation.dto.Activity;
import com.hishu.vacation.dto.CommonParam;
import com.hishu.vacation.dto.Coupon;
import com.hishu.vacation.dto.CouponPackage;
import com.hishu.vacation.dto.JsonResult;

@Controller
@RequestMapping("/operation")
public class OperationController {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private OperationService operationService;
	
	/* =============================优惠套餐管理 开始================================ */
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="type"),
            @ValidateFiled(index=0,notNull=true,filedName="content")
    })
	@Log
	@RequestMapping(value="/addCouponPackage.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addCouponPackage(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.addCouponPackage(couponPackage);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteCouponPackage.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteCouponPackage(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.deleteCouponPackage(couponPackage);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="type"),
            @ValidateFiled(index=0,filedName="content")
    })
	@Log
	@RequestMapping(value="/updateCouponPackage.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateCouponPackage(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.updateCouponPackage(couponPackage);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getCouponPackage.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCouponPackage(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		CouponPackage couponPackageInDB=operationService.getCouponPackage(couponPackage);
		result.setData(couponPackageInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getCouponPackageList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCouponPackageList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("likeName", param.getLikeName());
		String type=request.getParameter("type");
		map.put("type", type);
		map.put("startTime", param.getStartTime());
		map.put("endTime", param.getEndTime());
		String countStr=request.getParameter("count");
		if(StringUtils.isNotEmpty(countStr)){
			Integer count=Integer.parseInt(countStr);
			map.put("count", count);
		}
		String discountStr=request.getParameter("discount");
		if(StringUtils.isNotEmpty(discountStr)){
			Integer discount=Integer.parseInt(discountStr);
			map.put("discount", discount);
		}
		map.put("pageNum", param.getPageNum());
		map.put("offset", param.getOffset());
		
		List<CouponPackage>list=operationService.getCouponPackageList(map);
		int total=operationService.getCouponPackageListCount(map);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	/* =============================优惠套餐管理 介绍================================ */
	
	/* =============================优惠劵管理 开始================================ */
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,filedName="houseIds"),
            @ValidateFiled(index=0,filedName="packageId"),
            @ValidateFiled(index=0,filedName="descript"),
            @ValidateFiled(index=0,filedName="outOfDatetime"),
            @ValidateFiled(index=0,filedName="limit")
    })
	@Log
	@RequestMapping(value="/addCoupon.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addCoupon(Coupon coupon,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.addCoupon(coupon);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteCoupon.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteCoupon(Coupon coupon,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.deleteCoupon(coupon);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="houseIds"),
            @ValidateFiled(index=0,filedName="packageId"),
            @ValidateFiled(index=0,filedName="descript"),
            @ValidateFiled(index=0,filedName="outOfDatetime"),
            @ValidateFiled(index=0,filedName="limit")
    })
	@Log
	@RequestMapping(value="/updateCoupon.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateCoupon(Coupon coupon,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.updateCoupon(coupon);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getCoupon.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCoupon(Coupon coupon,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Coupon couponInDB=operationService.getCoupon(coupon);
		result.setData(couponInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getCouponList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCouponList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("likeName", param.getLikeName());
		String packageId=request.getParameter("packageId");
		if(StringUtils.isNotEmpty(packageId)){
			map.put("packageId", Integer.parseInt(packageId));
		}
		String houseIds=request.getParameter("houseIds");
		map.put("houseIds", houseIds);
		String outOfDatetime=request.getParameter("outOfDatetime");
		map.put("outOfDatetime", outOfDatetime);
		String limit=request.getParameter("limit");
		if(StringUtils.isNotEmpty(limit)){
			map.put("limit", Integer.parseInt(limit));
		}
		map.put("pageNum", param.getPageNum());
		map.put("offset", param.getOffset());
		
		List<Coupon>list=operationService.getCouponList(map);
		int total=operationService.getCouponListCount(map);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	/* =============================优惠劵管理 结束================================ */
	
	/* =============================活动管理 开始================================ */
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="type")
    })
	@RequestMapping(value="/getActivity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getActivity(Activity activity,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Activity activityInDB=operationService.getActivity(activity);
		result.setData(activityInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="remark"),
            @ValidateFiled(index=0,filedName="expiryTime")
    })
	@Log
	@RequestMapping(value="/updateActivity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateActivity(Activity activity,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		operationService.updateActivity(activity);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	/* =============================活动管理 结束================================ */
}
