package com.hivacation.webapp.biz.service;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.CouponPackage;
import com.hivacation.webapp.dto.House;

public interface ActionService {

	public Map<String,Object> getActionHouseInfo(Map<String,Object>param);
	
	public List<House>getHouseOfAction(Map<String,Object>param);
	
	public CouponPackage getCouponPackage(CouponPackage couponPackage,boolean loadHouseDetail);
}
