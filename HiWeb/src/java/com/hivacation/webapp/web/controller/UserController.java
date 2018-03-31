package com.hivacation.webapp.web.controller;

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
import com.hivacation.webapp.biz.service.UserService;
import com.hivacation.webapp.dto.JsonResult;
import com.hivacation.webapp.dto.UserInfo;

@Controller
@RequestMapping("/user")
public class UserController {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId")
    })
	@RequestMapping(value="/getUser.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getUser(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		UserInfo user=new UserInfo();
		
		result.setData(user);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
}
