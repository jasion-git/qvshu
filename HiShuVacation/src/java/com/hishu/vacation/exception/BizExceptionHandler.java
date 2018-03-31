package com.hishu.vacation.exception;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class BizExceptionHandler implements HandlerExceptionResolver {

	private String defaultErrorPage;
	private String ajaxErrorPage;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		//先打印异常
		e.printStackTrace();
		
		HandlerMethod h=(HandlerMethod) handler;
		Method method=h.getMethod();
		Map<String, Object>model=new HashMap<String, Object>();
		
		//如果是系统封装的异常
		if(e instanceof BizException){
			BizException ex=(BizException) e;
			model.put("code", ex.getErrorCode());//后续封装异常后，从异常中获取
	        model.put("msg", ex.getErrorMsg());
		}else{
			model.put("code", 1);//后续封装异常后，从异常中获取
	        model.put("msg", e.getMessage()==null?"系统异常":e.getMessage());
		}
		
		if(method.isAnnotationPresent(org.springframework.web.bind.annotation.ResponseBody.class)){
			//如果是有@ResponseBody注解的，则是ajax请求
        	//是ajax请求
			return new ModelAndView(ajaxErrorPage, model);
		}else{
			//返回页面
			return new ModelAndView(defaultErrorPage, model);
		}
	}

	public String getDefaultErrorPage() {
		return defaultErrorPage;
	}

	public void setDefaultErrorPage(String defaultErrorPage) {
		this.defaultErrorPage = defaultErrorPage;
	}

	public String getAjaxErrorPage() {
		return ajaxErrorPage;
	}

	public void setAjaxErrorPage(String ajaxErrorPage) {
		this.ajaxErrorPage = ajaxErrorPage;
	}
}
