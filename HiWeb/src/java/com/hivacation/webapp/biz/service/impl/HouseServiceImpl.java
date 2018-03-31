package com.hivacation.webapp.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hivacation.webapp.biz.dao.CityDao;
import com.hivacation.webapp.biz.dao.HouseDao;
import com.hivacation.webapp.biz.dao.ThirdPartyProductDao;
import com.hivacation.webapp.biz.service.HouseService;
import com.hivacation.webapp.dto.City;
import com.hivacation.webapp.dto.Comment;
import com.hivacation.webapp.dto.District;
import com.hivacation.webapp.dto.DistrictImg;
import com.hivacation.webapp.dto.House;
import com.hivacation.webapp.dto.HouseFacility;
import com.hivacation.webapp.dto.HouseImg;
import com.hivacation.webapp.dto.HouseReception;
import com.hivacation.webapp.dto.ThirdPartyProduct;

@Service
public class HouseServiceImpl implements HouseService{

	@Autowired
	private HouseDao houseDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private ThirdPartyProductDao thirdPartyProductDao;
	
	@Override
	public House getHouse(House house,boolean loadDetail) {
		House houseInDB=houseDao.getHouse(house);
		if(houseInDB!=null&&loadDetail){
			//加载房源图片
			List<HouseImg>houseImgList=houseDao.getHouseImgList(house);
			houseInDB.setHouseImgs(houseImgList);
			//加载房源设施
			List<HouseFacility>houseFacilityList=houseDao.getHouseFacilityList(house);
			houseInDB.setHouseFacilities(houseFacilityList);
			//加载小区周边
			List<DistrictImg>districtImgList=houseDao.getDistrictImgList(house);
			houseInDB.setDistrictImgList(districtImgList);
			//加载可接待人群
			List<HouseReception>houseReceptionList=houseDao.getHouseReceptionList(house);
			houseInDB.setHouseReceptions(houseReceptionList);
			//记载评论
			List<Comment>commentList=houseDao.getCommentList(house);
			houseInDB.setCommentList(commentList);
		}
		return houseInDB;
	}

	@Override
	public List<Map<String, Object>> getSimpleHouseList(
			Map<String, Object> param) {
		return houseDao.getSimpleHouseList(param);
	}

	@Override
	public List<District> getDistrictListOfCity(City city) {
		return cityDao.getDistrictListOfCity(city);
	}

	@Override
	public District getDistrictDetail(District district) {
		return cityDao.getDistrictDetail(district);
	}

	@Override
	public List<Map<String, Object>> getCityDistrictList(
			Map<String, Object> param) {
		return cityDao.getCityDistrictList(param);
	}

	@Override
	public List<Map<String, Object>> getHouseStatusList(
			Map<String, Object> param) {
		return houseDao.getHouseStatusList(param);
	}

	@Override
	public List<ThirdPartyProduct> getThirdPartyProductList(
			Map<String, Object> param) {
		return thirdPartyProductDao.getThirdPartyProductList(param);
	}

	
}
