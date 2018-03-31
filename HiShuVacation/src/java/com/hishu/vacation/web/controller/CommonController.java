package com.hishu.vacation.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hishu.vacation.annotation.Log;
import com.hishu.vacation.annotation.ValidateFiled;
import com.hishu.vacation.annotation.ValidateGroup;
import com.hishu.vacation.exception.BizException;
import com.hishu.vacation.biz.file.FileService;
import com.hishu.vacation.biz.file.FileServiceFactory;
import com.hishu.vacation.biz.service.AdminUserService;
import com.hishu.vacation.common.Constant;
import com.hishu.vacation.exception.ReturnCode;
import com.hishu.vacation.dto.AdminUser;
import com.hishu.vacation.dto.JsonResult;

@Controller
@RequestMapping("/common")
public class CommonController {

	protected final Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private AdminUserService adminUserService;
	
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
	    AdminUser userInDB=new AdminUser();
	    userInDB.setAccount(account);
	    userInDB=adminUserService.getAdminUser(userInDB);
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
		return result;
	}
	
	@RequestMapping(value="/unlogin.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult unlogin(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		result.setCode(ReturnCode.ERROR_USER_NO_LOGIN);
		result.setMsg("未登录");
		return result;
	}
	
	@RequestMapping(value="/uploadFile.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult uploadFile(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
		MultipartFile file=mulRequest.getFile("file");
		if(file!=null){
			//上传文件
			FileServiceFactory factory=FileServiceFactory.getInstance();
			FileService fileService=factory.getFileService(FileServiceFactory.TYPE_LOCAL);
			String fileName=file.getOriginalFilename();
			String webUrl=fileService.uploadFile(file.getBytes(), fileName, "");
			System.out.println(webUrl);
			result.setData(webUrl);
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
}
