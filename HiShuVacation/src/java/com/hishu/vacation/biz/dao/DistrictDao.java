package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.District;
import com.hishu.vacation.dto.DistrictImg;

public interface DistrictDao {

	public int addDistrict(District district);
	public int deleteDistrict(District district);
	public int updateDistrict(District district);
	public District getDistrict(District district);
	public List<District>getDistrictList(Map<String,Object>param);
	public int getDistrictListCount(Map<String,Object>param);
	
	public List<Map<String,Object>>getDistrictHouseList(Map<String,Object>param);
	public int getDistrictHouseListCount(Map<String,Object>param);
	
	public int addDistrictImg(DistrictImg districtImg);
	public int deleteDistrictImg(DistrictImg districtImg);
	public int updateDistrictImg(DistrictImg districtImg);
	public DistrictImg getDistrictImg(DistrictImg districtImg);
	public List<DistrictImg>getDistrictImgList(Map<String,Object>param);
	public int getDistrictImgListCount(Map<String,Object>param);
}
