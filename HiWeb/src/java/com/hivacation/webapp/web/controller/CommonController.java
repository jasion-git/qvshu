package com.hivacation.webapp.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hivacation.webapp.annotation.Log;
import com.hivacation.webapp.annotation.ValidateFiled;
import com.hivacation.webapp.annotation.ValidateGroup;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.biz.service.HouseService;
import com.hivacation.webapp.biz.service.UserService;
import com.hivacation.webapp.common.Constant;
import com.hivacation.webapp.common.SpringContextHolder;
import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.District;
import com.hivacation.webapp.dto.JsonResult;
import com.hivacation.webapp.dto.UserInfo;
import com.hivacation.webapp.dto.WeChatGZH;
import com.hivacation.webapp.exception.BizException;
import com.hivacation.webapp.exception.ReturnCode;

@Controller
@RequestMapping("/common")
public class CommonController {

	protected final Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private HouseService houseService;
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="account",maxLen=20,checkMaxLen=true),
            @ValidateFiled(index=0,notNull=true,filedName="password")
    })
	@Log
	@RequestMapping(value="/login.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult login(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String account=request.getParameter("account");
		String password=request.getParameter("password");
		
		//shiro登录
		UsernamePasswordToken token=new UsernamePasswordToken(account, password.toCharArray(), null);   
	    try{  
	        SecurityUtils.getSubject().login(token);
	    }catch (UnknownAccountException ex){
	    	log.error(ex);
	        throw new BizException(ReturnCode.ERROR_USER_PASSWORD_WRONG,"用户密码错误");
	    }catch (IncorrectCredentialsException ex){  
	    	log.error(ex);
	        throw new BizException(ReturnCode.ERROR_USER_PASSWORD_WRONG,"用户密码错误");
	    }catch (AuthenticationException ex){  
	    	log.error(ex);
	        throw new BizException(ReturnCode.ERROR_USER_NO_PERMISSION,"用户没有权限");
	    }catch (Exception ex) {
	    	log.error(ex);
	        throw new BizException(ReturnCode.SYSTEM_DEFAULT_ERROR,"系统异常");
	    }
	    //通过了登录
	    
	    //获取用户信息
	    UserInfo userInDB=new UserInfo();
	    userInDB.setAccount(account);
	    userInDB=userService.getUserByAccount(userInDB,true);
	    userInDB.setPassword(null);//清空密码信息
	    //把用户信息放在session
	    request.getSession().setAttribute(Constant.USER_SESSION_KEY, userInDB);
	    
	    result.setCode(JsonResult.CODE_SUCCESS);
	    result.setData(userInDB);//返回用户信息
		return result;
	}
	
	@Log
	@RequestMapping(value="/logout.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult logout(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Subject subject=SecurityUtils.getSubject();
	    if(subject!=null){
	        try{
	        	//退出shiro登录
	            subject.logout();
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }
	    }
	    //清空session信息
	    request.getSession().setAttribute(Constant.USER_SESSION_KEY, null);
	    //request.getSession().invalidate();
	    result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/unauthorized.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult unauthorized(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		result.setCode(ReturnCode.ERROR_USER_NO_PERMISSION);
		result.setMsg("没有权限");
		
		CommonService commonService=SpringContextHolder.getBean("commonService");
		
		WeChatGZH w=new WeChatGZH();
		w.setAccount("gh_52c1a5899c88");
		w=commonService.getWeChatGZH(w);
		System.out.println(w);
		
		commonService.setStringInCache("pageAccessToken", "123456789", 60);
		String accessToken=commonService.getStringInCache("pageAccessToken");
		System.out.println(accessToken);
		
		return result;
	}
	
	@RequestMapping(value="/unlogin.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult unlogin(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		result.setCode(ReturnCode.ERROR_USER_NO_LOGIN);
		result.setMsg("未登录");
		return result;
	}
	
	@RequestMapping(value="/getHouseListOfHot.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseListOfHot(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		List<Map<String,Object>>list=commonService.getHouseListOfHot(param);
		result.setData(list);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getCityListOfHot.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCityListOfHot(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String pageSizeStr=request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSizeStr)){
			param.put("pageSize", Integer.parseInt(pageSizeStr));
		}
		List<City>list=commonService.getCityListOfHot(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("cityList", list);
		if(list!=null&&!list.isEmpty()){
			//查询第一个城市的小区
			City city=list.get(0);
			List<District>districtList=houseService.getDistrictListOfCity(city);
			ret.put("districtList", districtList);
		}
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="cityId")
    })
	@RequestMapping(value="/getHouseListOfCity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseListOfCity(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String pageSizeStr=request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSizeStr)){
			param.put("pageSize", Integer.parseInt(pageSizeStr));
		}else{
			param.put("pageSize", 2);
		}
		String cityIdStr=request.getParameter("cityId");
		Integer cityId=Integer.parseInt(cityIdStr);
		param.put("cityId", cityId);
		List<Map<String,Object>>houseList=commonService.getHouseListOfCity(param);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("houseList", houseList);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getHouseListOfRecommended.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseListOfRecommended(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String pageSizeStr=request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSizeStr)){
			param.put("pageSize", Integer.parseInt(pageSizeStr));
		}else{
			param.put("pageSize", 2);
		}
		List<Map<String,Object>>houseList=commonService.getHouseListOfRecommended(param);
		result.setData(houseList);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
}
