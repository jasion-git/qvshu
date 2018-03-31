package com.hivacation.webapp.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hivacation.webapp.common.CommonUtils;
import com.hivacation.webapp.common.Constant;
import com.hivacation.webapp.dto.UserInfo;
import com.hivacation.webapp.exception.BizException;
import com.hivacation.webapp.exception.ReturnCode;



@Component
@Aspect
public class LogAop implements Ordered{

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired  
	HttpServletRequest request;
	
	@AfterReturning(value="@annotation(com.hivacation.webapp.annotation.Log)", returning = "returnVal")
    public void afterReturin(Object returnVal){
		log.debug("------------------------正常结束---------------------------");
		log(0);
    }
	@AfterThrowing(value="@annotation(com.hivacation.webapp.annotation.Log)", throwing = "e")
    public void afterThrowing(Throwable e) {
		log.debug("------------------------异常结束---------------------------");
        if(e instanceof BizException){
        	BizException ex=(BizException) e;
        	log(ex.getErrorCode());
        }else{
        	log(ReturnCode.SYSTEM_DEFAULT_ERROR);
        }
    }
	
	private void log(Integer errorCode){
		if(request==null){
			return;
		}
		//地点：从url中解析出ip信息
		String ip=CommonUtils.getRequestIp(request);
		//人物：获取用户信息
		UserInfo userInSession=(UserInfo) request.getSession().getAttribute(Constant.USER_SESSION_KEY);
		if(userInSession==null){
			//用户信息为空，不记录日志
			return;
		}
		
		//事件：从url解析出模块信息和动作
		String uri=request.getRequestURI();
		if(StringUtils.isEmpty(uri)){
			//如果解析不出uri，也不记录日志
			return;
		}
		String[]u=uri.split("/");
		if(u.length<3){
			return;
		}
		String module=u[2];
		
		if(errorCode==null){
			errorCode=0;//没有错误返回码，则表示成功，成功是0
		}
		
		log.debug("地点:"+ip+",人物:"+userInSession.getId()+",事件:"+module+"-"+uri+",结果:"+errorCode);
	}
	
	/**
	 * 从uri中解析出做了什么事件
	 * @param uri
	 * @return
	 */
	private String conver(String uri){
		return uri;
	}
	
	/** 
	* 根据目标方法和注解类型  得到该目标方法的指定注解 
	*/  
	public Annotation getAnnotationByMethod(Method method , Class<?> annoClass){
		Annotation all[] = method.getAnnotations();  
	    for (Annotation annotation : all) {  
	    	if (annotation.annotationType() == annoClass) {  
	    		return annotation;  
	        }  
	    }
	    return null;  
	 }
	 
	/** 
	* 根据类和方法名得到方法 
	*/  
	public Method getMethodByClassAndName(Class<?> c , String methodName){  
		Method[] methods = c.getDeclaredMethods();  
	    for (Method method : methods) {  
	    	if(method.getName().equals(methodName)){  
	    		return method ;  
	        }
	    }  
	    return null;  
	}
	
	@Override
	public int getOrder() {
		//最先处理日志
		return 999;
	}
}
