package com.hivacation.webapp.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import com.hivacation.webapp.annotation.ValidateFiled;
import com.hivacation.webapp.annotation.ValidateGroup;
import com.hivacation.webapp.biz.service.HouseService;
import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.District;
import com.hivacation.webapp.dto.House;
import com.hivacation.webapp.dto.JsonResult;
import com.hivacation.webapp.dto.ThirdPartyProduct;

@Controller
@RequestMapping("/house")
public class HouseController {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private HouseService houseService;
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getHouse.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouse(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		House houseInDB=houseService.getHouse(house,true);
		result.setData(houseInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="cityId")
    })
	@RequestMapping(value="/getDistrictListOfCity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getDistrictListOfCity(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String cityIdStr=request.getParameter("cityId");
		Integer cityId=Integer.parseInt(cityIdStr);
		City city=new City();
		city.setId(cityId);
		List<District>districtList=houseService.getDistrictListOfCity(city);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", districtList);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getDistrictDetail.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getDistrictDetail(District district,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		District districtInDB=houseService.getDistrictDetail(district);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("district", districtInDB);
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("districtId", districtInDB.getId());
		List<Map<String,Object>>list=houseService.getSimpleHouseList(param);
		ret.put("houseList", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getHouseList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseList(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String cityIdStr=request.getParameter("cityId");
		if(StringUtils.isNotEmpty(cityIdStr)){
			param.put("cityId", Integer.parseInt(cityIdStr));
		}
		String liveCountStr=request.getParameter("liveCount");
		if(StringUtils.isNotEmpty(liveCountStr)){
			param.put("liveCount", Integer.parseInt(liveCountStr));
		}
		String amountStr=request.getParameter("amount");
		if(StringUtils.isNotEmpty(amountStr)){
			param.put("orderAmount", Double.parseDouble(amountStr));
		}
		
		List<Map<String,Object>>list=houseService.getSimpleHouseList(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getCityDistrictList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCityDistrictList(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		List<Map<String,Object>>list=houseService.getCityDistrictList(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getHouseStatus.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseStatus(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("houseId", house.getId());
		List<Map<String,Object>>houseList=houseService.getSimpleHouseList(param);
		if(houseList!=null&&!houseList.isEmpty()){
			Map<String,Object>ret=new HashMap<String,Object>();
			
			Map<String,Object>map=houseList.get(0);
			ret.put("house", map);
			//获取房源房态，当天到下个月最后一天的房态
			Date d=new Date();
			Calendar c=Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			
			Date now=c.getTime();
			c.add(Calendar.MONTH, 1);
			//获取某月最大天数  
	        int lastDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        //设置日历中月份的最大天数  
	        c.set(Calendar.DAY_OF_MONTH, lastDay);
	        c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			
	        Date lastDayOfNextMonth=c.getTime();
	        param.put("startTime", now);
	        param.put("endTime", lastDayOfNextMonth);
			List<Map<String,Object>>houseStatusList=houseService.getHouseStatusList(param);
			ret.put("houseStatusList", houseStatusList);
			
			result.setData(ret);
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="districtId")
    })
	@RequestMapping(value="/getThirdPartyProductList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getThirdPartyProductList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		Integer districtId=Integer.parseInt(request.getParameter("districtId"));
		param.put("districtId", districtId);
		List<ThirdPartyProduct>thirdPartyProductList=houseService.getThirdPartyProductList(param);
		result.setData(thirdPartyProductList);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	
	public static void main(String[]args)throws Exception{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=new Date();
		Date t=df.parse("2018-02-01 01:12:23");
		Calendar c=Calendar.getInstance();
		c.setTime(t);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		Date now=c.getTime();
		c.add(Calendar.MONTH, 1);
		//获取某月最大天数  
        int lastDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);  
        //设置日历中月份的最大天数  
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		
        Date lastDayOfNextMonth=c.getTime();
        
        System.out.println(df.format(now));
        System.out.println(df.format(lastDayOfNextMonth));
	}
}
