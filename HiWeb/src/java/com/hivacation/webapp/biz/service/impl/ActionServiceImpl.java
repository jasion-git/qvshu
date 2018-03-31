package com.hivacation.webapp.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hivacation.webapp.biz.dao.CouponPackageDao;
import com.hivacation.webapp.biz.dao.HouseDao;
import com.hivacation.webapp.biz.service.ActionService;
import com.hivacation.webapp.dto.CouponPackage;
import com.hivacation.webapp.dto.House;
import com.hivacation.webapp.dto.HouseAttribute;
import com.hivacation.webapp.dto.HouseImg;

@Service
public class ActionServiceImpl implements ActionService {

	@Autowired
	private CouponPackageDao couponPackageDao;
	@Autowired
	private HouseDao houseDao;
	
	@Override
	public Map<String, Object> getActionHouseInfo(Map<String, Object> param) {
		Integer actionId=(Integer) param.get("actionId");
		Integer houseId=(Integer) param.get("houseId");
		
		CouponPackage couponPackage=new CouponPackage();
		couponPackage.setId(actionId);
		couponPackage=couponPackageDao.getCouponPackage(couponPackage);
		
		House house=new House();
		house.setId(houseId);
		house=houseDao.getHouse(house);
		if(house!=null){
			//加载图片信息
			List<HouseImg>houseImgs=houseDao.getHouseImgs(param);
			house.setHouseImgs(houseImgs);
		}
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("action", couponPackage);
		ret.put("house", house);
		
		return ret;
	}

	@Override
	public List<House> getHouseOfAction(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CouponPackage getCouponPackage(CouponPackage couponPackage,boolean loadHouseDetail) {
		CouponPackage couponPackageInDB=couponPackageDao.getCouponPackage(couponPackage);
		if(couponPackageInDB==null){
			return null;
		}
		//查询所有关联的房源
		if(StringUtils.isNotEmpty(couponPackageInDB.getHouseIds())&&loadHouseDetail){
			Map<String,Object>param=new HashMap<String,Object>();
			param.put("houseIds", couponPackageInDB.getHouseIds());
			List<House>houses=houseDao.getHouseList(param);
			if(houses!=null){
				for(House house:houses){
					//查询房源的图片信息
					param.clear();
					param.put("houseId", house.getId());
					List<HouseImg>houseImgs=houseDao.getHouseImgs(param);
					house.setHouseImgs(houseImgs);
					//查询房源的属性信息
					List<HouseAttribute>houseAttributes=houseDao.getHouseAttributes(param);
					house.setHouseAttributes(houseAttributes);
				}
			}
			couponPackageInDB.setHouses(houses);
		}
		
		return couponPackageInDB;
	}

}
