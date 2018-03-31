package com.hishu.vacation.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hishu.vacation.biz.dao.CouponPackageDao;
import com.hishu.vacation.biz.service.ActionService;
import com.hishu.vacation.dto.CouponPackage;
@Service
public class ActionServiceImpl implements ActionService {

	@Autowired
	private CouponPackageDao couponPackageDao;
	
	@Override
	public int addCouponPackage(CouponPackage couponPackage) {
		return couponPackageDao.addCouponPackage(couponPackage);
	}

	@Override
	public int deleteCouponPackage(CouponPackage couponPackage) {
		return couponPackageDao.deleteCouponPackage(couponPackage);
	}

	@Override
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

}
