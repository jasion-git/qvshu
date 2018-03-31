package com.hishu.vacation.biz.service;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Activity;
import com.hishu.vacation.dto.Coupon;
import com.hishu.vacation.dto.CouponPackage;

public interface OperationService {

	public int addCouponPackage(CouponPackage couponPackage);
	public int deleteCouponPackage(CouponPackage couponPackage);
	public int updateCouponPackage(CouponPackage couponPackage);
	public CouponPackage getCouponPackage(CouponPackage couponPackage);
	public List<CouponPackage>getCouponPackageList(Map<String,Object>param);
	public int getCouponPackageListCount(Map<String,Object>param);

	public int addCoupon(Coupon coupon);
	public int deleteCoupon(Coupon coupon);
	public int updateCoupon(Coupon coupon);
	public Coupon getCoupon(Coupon coupon);
	public List<Coupon>getCouponList(Map<String,Object>param);
	public int getCouponListCount(Map<String,Object>param);
	
	public int addActivity(Activity activity);
	public int deleteActivity(Activity activity);
	public int updateActivity(Activity activity);
	public Activity getActivity(Activity activity);
	public List<Activity>getActivityList(Map<String,Object>param);
	public int getActivityListCount(Map<String,Object>param);
}
