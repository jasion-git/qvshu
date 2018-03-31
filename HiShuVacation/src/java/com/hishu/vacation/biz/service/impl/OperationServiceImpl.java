package com.hishu.vacation.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hishu.vacation.biz.dao.ActivityDao;
import com.hishu.vacation.biz.dao.CouponDao;
import com.hishu.vacation.biz.dao.CouponPackageDao;
import com.hishu.vacation.biz.service.OperationService;
import com.hishu.vacation.dto.Activity;
import com.hishu.vacation.dto.Coupon;
import com.hishu.vacation.dto.CouponPackage;
import com.hishu.vacation.dto.UserInfo;

@Transactional
@Service
public class OperationServiceImpl implements OperationService {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private CouponPackageDao couponPackageDao;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addCouponPackage(CouponPackage couponPackage) {
		return couponPackageDao.addCouponPackage(couponPackage);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteCouponPackage(CouponPackage couponPackage) {
		return couponPackageDao.deleteCouponPackage(couponPackage);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateCouponPackage(CouponPackage couponPackage) {
		return couponPackageDao.updateCouponPackage(couponPackage);
	}

	@Override
	public CouponPackage getCouponPackage(CouponPackage couponPackage) {
		return couponPackageDao.getCouponPackage(couponPackage);
	}

	@Override
	public List<CouponPackage> getCouponPackageList(Map<String, Object> param) {
		return couponPackageDao.getCouponPackageList(param);
	}

	@Override
	public int getCouponPackageListCount(Map<String, Object> param) {
		return couponPackageDao.getCouponPackageListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addCoupon(Coupon coupon) {
		//给合适人群加上优惠券 TODO
		List<UserInfo>userList=new ArrayList<UserInfo>();
		
		return couponDao.addCoupon(coupon);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteCoupon(Coupon coupon) {
		return couponDao.deleteCoupon(coupon);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateCoupon(Coupon coupon) {
		return couponDao.updateCoupon(coupon);
	}

	@Override
	public Coupon getCoupon(Coupon coupon) {
		return couponDao.getCoupon(coupon);
	}

	@Override
	public List<Coupon> getCouponList(Map<String, Object> param) {
		return couponDao.getCouponList(param);
	}

	@Override
	public int getCouponListCount(Map<String, Object> param) {
		return couponDao.getCouponListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addActivity(Activity activity) {
		return activityDao.addActivity(activity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteActivity(Activity activity) {
		return activityDao.deleteActivity(activity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateActivity(Activity activity) {
		return activityDao.updateActivity(activity);
	}

	@Override
	public Activity getActivity(Activity activity) {
		return activityDao.getActivity(activity);
	}

	@Override
	public List<Activity> getActivityList(Map<String, Object> param) {
		return activityDao.getActivityList(param);
	}

	@Override
	public int getActivityListCount(Map<String, Object> param) {
		return activityDao.getActivityListCount(param);
	}

}
