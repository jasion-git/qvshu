package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Coupon;

public interface CouponDao {

	public int addCoupon(Coupon coupon);
	public int deleteCoupon(Coupon coupon);
	public int updateCoupon(Coupon coupon);
	public Coupon getCoupon(Coupon coupon);
	public List<Coupon>getCouponList(Map<String,Object>param);
	public int getCouponListCount(Map<String,Object>param);
}
