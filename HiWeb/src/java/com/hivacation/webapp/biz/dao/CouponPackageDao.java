package com.hivacation.webapp.biz.dao;

import java.util.List;
import java.util.Map;

import com.hivacation.webapp.dto.CouponPackage;


public interface CouponPackageDao {

	public int addCouponPackage(CouponPackage couponPackage);
	public int deleteCouponPackage(CouponPackage couponPackage);
	public int updateCouponPackage(CouponPackage couponPackage);
	public CouponPackage getCouponPackage(CouponPackage couponPackage);
	public List<CouponPackage>getCouponPackageList(Map<String,Object>param);
	public int getCouponPackageListCount(Map<String,Object>param);
}
