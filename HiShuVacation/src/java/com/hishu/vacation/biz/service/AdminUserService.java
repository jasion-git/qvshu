package com.hishu.vacation.biz.service;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.AdminUser;
import com.hishu.vacation.dto.Auth;
import com.hishu.vacation.dto.City;
import com.hishu.vacation.dto.District;
import com.hishu.vacation.dto.DistrictImg;
import com.hishu.vacation.dto.HouseImg;
import com.hishu.vacation.dto.Module;
import com.hishu.vacation.dto.Provice;
import com.hishu.vacation.dto.Role;
import com.hishu.vacation.dto.ThirdPartRoomStatus;

public interface AdminUserService {

	public int addAdminUser(AdminUser adminUser);
	public int deleteAdminUser(AdminUser adminUser);
	public int updateAdminUser(AdminUser adminUser);
	public AdminUser getAdminUser(AdminUser adminUser);
	public List<AdminUser>getAdminUserList(Map<String,Object>param);
	public int getAdminUserListCount(Map<String,Object>param);
	
	public int addRole(Role role);
	public int deleteRole(Role role);
	public int updateRole(Role role);
	public Role getRole(Role role);
	public List<Role>getRoleList(Map<String,Object>param);
	public int getRoleListCount(Map<String,Object>param);
	
	public List<Module>getModuleListByRoleId(Map<String,Object>param);
	public List<Auth>getAuthListByRoleId(Map<String,Object>param);
	
	public int addProvice(Provice provice);
	public int deleteProvice(Provice provice);
	public int updateProvice(Provice provice);
	public Provice getProvice(Provice provice);
	public List<Provice>getProviceList(Map<String,Object>param);
	public int getProviceListCount(Map<String,Object>param);
	
	public int addCity(City city);
	public int deleteCity(City city);
	public int updateCity(City city);
	public City getCity(City city);
	public List<City>getCityList(Map<String,Object>param);
	public int getCityListCount(Map<String,Object>param);
	
	public int addDistrict(District district);
	public int deleteDistrict(District district);
	public int updateDistrict(District district);
	public District getDistrict(District district);
	public List<District>getDistrictList(Map<String,Object>param);
	public int getDistrictListCount(Map<String,Object>param);
	
	public int addDistrictImg(DistrictImg districtImg);
	public int deleteDistrictImg(DistrictImg districtImg);
	public int updateDistrictImg(DistrictImg districtImg);
	public DistrictImg getDistrictImg(DistrictImg districtImg);
	public List<DistrictImg>getDistrictImgList(Map<String,Object>param);
	public int getDistrictImgListCount(Map<String,Object>param);
	
	public List<Map<String,Object>>getDistrictHouseList(Map<String,Object>param);
	public int getDistrictHouseListCount(Map<String,Object>param);
	
	public int addAuth(Auth auth);
	public int deleteAuth(Auth auth);
	public int updateAuth(Auth auth);
	public Auth getAuth(Auth auth);
	public List<Auth>getAuthList(Map<String,Object>param);
	public int getAuthListCount(Map<String,Object>param);
	
	public int addModule(Module module);
	public int deleteModule(Module module);
	public int updateModule(Module module);
	public Module getModule(Module module);
	public List<Module>getModuleList(Map<String,Object>param);
	public int getModuleListCount(Map<String,Object>param);
	
	public int addThirdPartRoomStatus(List<ThirdPartRoomStatus>list);
	
	public List<Map<String,Object>>getThirdPartHouseList(Map<String,Object>param);
}
