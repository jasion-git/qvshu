package com.hivacation.webapp.web.controller;

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

import com.hivacation.webapp.annotation.Log;
import com.hivacation.webapp.annotation.ValidateFiled;
import com.hivacation.webapp.annotation.ValidateGroup;
import com.hivacation.webapp.biz.service.ActionService;
import com.hivacation.webapp.biz.service.CommonService;
import com.hivacation.webapp.dto.ActionUserRecord;
import com.hivacation.webapp.dto.CouponPackage;
import com.hivacation.webapp.dto.JsonResult;
import com.hivacation.webapp.dto.SubscribeRecord;
import com.hivacation.webapp.exception.BizException;
import com.hivacation.webapp.exception.ReturnCode;

@Controller
@RequestMapping("/action")
public class ActionController {

	protected final Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private CommonService commonService;
	@Autowired
	private ActionService actionService;
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId"),
            @ValidateFiled(index=0,notNull=true,filedName="actionId"),
            @ValidateFiled(index=0,notNull=true,filedName="houseId")
    })
	@Log
	@RequestMapping(value="/kanjia.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult kanjia(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//先看openId是否有效
		String openId=request.getParameter("openId");
		String actionId=request.getParameter("actionId");
		String houseId=request.getParameter("houseId");
		String friendOpenId=request.getParameter("friendOpenId");
		
		SubscribeRecord record=new SubscribeRecord();
		record.setOpenId(openId);
		record=commonService.getSubscribeRecord(record);
		if(record==null){
			throw new BizException(ReturnCode.ERROR_WECHAT_OPENID_INVALIDATE,"用户openId无效");
		}
		
		Double actionAmount=new Double(2000);//TODO
		
		/*
		 *  砍了自己，就不能砍别人,也不能继续为自己砍价
			砍了别人，还能砍自己
		 * */
		if(StringUtils.isNotEmpty(friendOpenId)&&!openId.equals(friendOpenId)){
			//帮朋友砍价
			Map<String,Object>param=new HashMap<String,Object>();
			param.put("openId", openId);
			param.put("actionId", actionId);
			param.put("houseId", houseId);
			List<ActionUserRecord>list=commonService.getActionUserRecordList(param);
			for(ActionUserRecord r:list){
				if(openId.equals(r.getFriendOpenId())){
					//已经自己为自己砍了价，则不能为朋友砍价
					throw new BizException(ReturnCode.ERROR_KANJIA_ACTION_HAD_KANJIA_SELF,"自己已经为自己砍了价，则不再为朋友砍价");
				}else if(friendOpenId.equals(r.getFriendOpenId())){
					//已经自己为自己砍了价，则不能为朋友砍价
					throw new BizException(ReturnCode.ERROR_KANJIA_ACTION_HAD_KANJIA_FRIEND,"自己已经为该朋友砍了价，则不再为朋友砍价");
				}
			}
			
			//如果是0元了，则不能再继续砍价
			param.put("openId", null);
			param.put("friendOpenId", friendOpenId);
			list=commonService.getActionUserRecordList(param);
			Double amount=new Double(0);
			for(ActionUserRecord r:list){
				amount+=r.getAmount();
			}
			if((actionAmount-amount)<=0){
				throw new BizException(ReturnCode.ERROR_KANJIA_ACTION_HAD_0,"已经砍到0元");
			}
			
			//如果通过，则增加一条砍价活动记录
			Double kanjia=new Double(50);//TODO 随即产生一个金额
			ActionUserRecord addRecord=new ActionUserRecord();
			addRecord.setActionId(Integer.parseInt(actionId));
			addRecord.setHouseId(Integer.parseInt(houseId));
			addRecord.setOpenId(openId);
			addRecord.setFriendOpenId(friendOpenId);
			addRecord.setAmount(kanjia);
			commonService.addActionUserRecord(addRecord);
			
			result.setData(kanjia);//返回砍到的价格
		}else{
			//为自己砍价，如果已经为自己砍过假了，则不能再砍价
			Map<String,Object>param=new HashMap<String,Object>();
			param.put("openId", openId);
			param.put("friendOpenId", friendOpenId);
			param.put("actionId", actionId);
			param.put("houseId", houseId);
			List<ActionUserRecord>list=commonService.getActionUserRecordList(param);
			if(list!=null&&!list.isEmpty()){
				//还要即时查询是否砍到了0元
				param.put("openId", null);//查所有为他砍价的记录
				list=commonService.getActionUserRecordList(param);
				Double amount=new Double(0);
				for(ActionUserRecord r:list){
					amount+=r.getAmount();
				}
				//提示用户已经砍过价格了，并返回当前砍到的价格
				throw new BizException(ReturnCode.ERROR_KANJIA_ACTION_HAD_KANJIA,""+(actionAmount-amount));
			}
			
			//如果通过，则增加一条砍价活动记录//TODO 随即产生一个金额
			Double kanjia=new Double(50);
			ActionUserRecord addRecord=new ActionUserRecord();
			addRecord.setActionId(Integer.parseInt(actionId));
			addRecord.setHouseId(Integer.parseInt(houseId));
			addRecord.setOpenId(openId);
			addRecord.setFriendOpenId(openId);//为自己砍价
			addRecord.setAmount(new Double(50));
			commonService.addActionUserRecord(addRecord);
			result.setData(kanjia);//返回砍到的价格
		}
		
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId"),
            @ValidateFiled(index=0,notNull=true,filedName="actionId"),
            @ValidateFiled(index=0,notNull=true,filedName="houseId")
    })
	@RequestMapping(value="/getKanJiaStatus.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getKanJiaStatus(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String openId=request.getParameter("openId");
		String actionId=request.getParameter("actionId");
		String houseId=request.getParameter("houseId");
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("openId", openId);
		param.put("friendOpenId", openId);
		param.put("actionId", actionId);
		param.put("houseId", houseId);
		List<ActionUserRecord>list=commonService.getActionUserRecordList(param);
		if(list!=null&&!list.isEmpty()){
			throw new BizException(ReturnCode.ERROR_KANJIA_ACTION_HAD_KANJIA,"用户已经砍过价了");
		}
		//查询活动与房源的信息
		Map<String,Object>para=new HashMap<String,Object>();
		para.put("actionId", Integer.parseInt(actionId));
		para.put("houseId", Integer.parseInt(houseId));
		Map<String,Object>ret=actionService.getActionHouseInfo(para);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId"),
            @ValidateFiled(index=0,notNull=true,filedName="actionId"),
            @ValidateFiled(index=0,notNull=true,filedName="houseId")
    })
	@RequestMapping(value="/getKanJiaSum.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getKanJiaSum(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String openId=request.getParameter("openId");
		String actionId=request.getParameter("actionId");
		String houseId=request.getParameter("houseId");
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("friendOpenId", openId);
		param.put("actionId", actionId);
		param.put("houseId", houseId);
		List<ActionUserRecord>list=commonService.getActionUserRecordList(param);
		Double amount=new Double(0);
		if(list!=null){
			for(ActionUserRecord r:list){
				amount+=r.getAmount();
			}
		}
		
		//查询活动与房源的信息
		Map<String,Object>para=new HashMap<String,Object>();
		para.put("actionId", Integer.parseInt(actionId));
		para.put("houseId", Integer.parseInt(houseId));
		Map<String,Object>ret=actionService.getActionHouseInfo(para);
		
		ret.put("amount", amount);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="openId")
    })
	@RequestMapping(value="/getKanJiaFiendList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getKanJiaFiendList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String openId=request.getParameter("openId");
		String actionId=request.getParameter("actionId");
		String houseId=request.getParameter("houseId");
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("openId", openId);//不查他本身出来
		param.put("friendOpenId", openId);
		param.put("actionId", actionId);
		param.put("houseId", houseId);
		String pageSizeStr=request.getParameter("pageSize");
		if(StringUtils.isNotEmpty(pageSizeStr)){
			Integer pageSize=Integer.parseInt(pageSizeStr);
			Integer pageNum=1;
			Integer offset=(pageNum-1)*pageSize;
			param.put("pageSize", pageSize);
			param.put("offset", offset);
		}
		List<Map<String,Object>>list=commonService.getKanJiaFriendList(param);
		result.setData(list);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="actionId"),
            @ValidateFiled(index=0,notNull=true,filedName="houseId")
    })
	@RequestMapping(value="/getActionInfoAndHouseInfo.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getActionInfoAndHouseInfo(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String actionIdStr=request.getParameter("actionId");
		String houseIdStr=request.getParameter("houseId");
		
		Integer actionId=Integer.parseInt(actionIdStr);
		Integer houseId=Integer.parseInt(houseIdStr);
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("actionId", actionId);
		param.put("houseId", houseId);
		Map<String,Object>ret=actionService.getActionHouseInfo(param);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getActionDetails.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getActionDetails(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		CouponPackage couponPackageInDB=actionService.getCouponPackage(couponPackage, true);
		result.setData(couponPackageInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
		
}
