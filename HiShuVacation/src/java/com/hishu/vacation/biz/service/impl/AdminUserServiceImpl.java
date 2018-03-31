package com.hishu.vacation.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hishu.vacation.biz.dao.AdminUserDao;
import com.hishu.vacation.biz.dao.AuthDao;
import com.hishu.vacation.biz.dao.CityDao;
import com.hishu.vacation.biz.dao.DistrictDao;
import com.hishu.vacation.biz.dao.HouseDao;
import com.hishu.vacation.biz.dao.HouseDayStatusDao;
import com.hishu.vacation.biz.dao.ModuleDao;
import com.hishu.vacation.biz.dao.ProviceDao;
import com.hishu.vacation.biz.dao.RoleDao;
import com.hishu.vacation.biz.dao.ThirdPartRoomStatusDao;
import com.hishu.vacation.biz.service.AdminUserService;
import com.hishu.vacation.dto.AdminUser;
import com.hishu.vacation.dto.Auth;
import com.hishu.vacation.dto.City;
import com.hishu.vacation.dto.District;
import com.hishu.vacation.dto.DistrictImg;
import com.hishu.vacation.dto.House;
import com.hishu.vacation.dto.HouseDayStatus;
import com.hishu.vacation.dto.Module;
import com.hishu.vacation.dto.Provice;
import com.hishu.vacation.dto.Role;
import com.hishu.vacation.dto.ThirdPartRoomStatus;

@Transactional
@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ProviceDao proviceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private AuthDao authDao;
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private ThirdPartRoomStatusDao thirdPartRoomStatusDao;
	@Autowired
	private HouseDao houseDao;
	@Autowired
	private HouseDayStatusDao houseDayStatusDao;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int addAdminUser(AdminUser adminUser) {
		return adminUserDao.addAdminUser(adminUser);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteAdminUser(AdminUser adminUser) {
		return adminUserDao.deleteAdminUser(adminUser);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateAdminUser(AdminUser adminUser) {
		return adminUserDao.updateAdminUser(adminUser);
	}

	@Override
	public AdminUser getAdminUser(AdminUser adminUser) {
		AdminUser user=adminUserDao.getAdminUser(adminUser);
		if(user!=null){
			Role role=new Role();
			role.setId(user.getRoleId());
			role=roleDao.getRole(role);
			user.setRole(role);
			if(role!=null){
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("roleId", user.getRoleId());
				if("admin".equals(role.getCode())){
					//如果是管理员，则查询全部
					param.put("roleId", null);
				}
				//加载用户的信息
				List<Module>modules=roleDao.getModuleListByRoleId(param);
				List<Auth>auths=roleDao.getAuthListByRoleId(param);
				role.setModules(modules);
				role.setAuths(auths);
			}
		}
		
		return user;
	}

	@Override
	public List<AdminUser> getAdminUserList(Map<String, Object> param) {
		return adminUserDao.getAdminUserList(param);
	}

	@Override
	public int getAdminUserListCount(Map<String, Object> param) {
		return adminUserDao.getAdminUserListCount(param);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int addRole(Role role) {
		return roleDao.addRole(role);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteRole(Role role) {
		return roleDao.deleteRole(role);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateRole(Role role) {
		return roleDao.updateRole(role);
	}

	@Override
	public Role getRole(Role role) {
		return roleDao.getRole(role);
	}

	@Override
	public List<Role> getRoleList(Map<String, Object> param) {
		return roleDao.getRoleList(param);
	}

	@Override
	public int getRoleListCount(Map<String, Object> param) {
		return roleDao.getRoleListCount(param);
	}
	
	@Override
	public List<Module> getModuleListByRoleId(Map<String, Object> param) {
		return roleDao.getModuleListByRoleId(param);
	}

	@Override
	public List<Auth> getAuthListByRoleId(Map<String, Object> param) {
		return roleDao.getAuthListByRoleId(param);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int addProvice(Provice provice) {
		return proviceDao.addProvice(provice);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteProvice(Provice provice) {
		return proviceDao.deleteProvice(provice);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateProvice(Provice provice) {
		return proviceDao.updateProvice(provice);
	}

	@Override
	public Provice getProvice(Provice provice) {
		return proviceDao.getProvice(provice);
	}

	@Override
	public List<Provice> getProviceList(Map<String, Object> param) {
		return proviceDao.getProviceList(param);
	}

	@Override
	public int getProviceListCount(Map<String, Object> param) {
		return proviceDao.getProviceListCount(param);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int addCity(City city) {
		return cityDao.addCity(city);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteCity(City city) {
		return cityDao.deleteCity(city);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateCity(City city) {
		return cityDao.updateCity(city);
	}

	@Override
	public City getCity(City city) {
		return cityDao.getCity(city);
	}

	@Override
	public List<City> getCityList(Map<String, Object> param) {
		return cityDao.getCityList(param);
	}

	@Override
	public int getCityListCount(Map<String, Object> param) {
		return cityDao.getCityListCount(param);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int addDistrict(District district) {
		return districtDao.addDistrict(district);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteDistrict(District district) {
		return districtDao.deleteDistrict(district);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateDistrict(District district) {
		return districtDao.updateDistrict(district);
	}

	@Override
	public District getDistrict(District district) {
		District districtInDB=districtDao.getDistrict(district);
		if(districtInDB!=null){
			//加载图片信息
			Map<String,Object>param=new HashMap<String,Object>();
			param.put("districtId", districtInDB.getId());
			List<DistrictImg>imgs=districtDao.getDistrictImgList(param);
			districtInDB.setDistrictImgs(imgs);
		}
		return districtInDB;
	}

	@Override
	public List<District> getDistrictList(Map<String, Object> param) {
		return districtDao.getDistrictList(param);
	}

	@Override
	public int getDistrictListCount(Map<String, Object> param) {
		return districtDao.getDistrictListCount(param);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int addAuth(Auth auth) {
		return authDao.addAuth(auth);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteAuth(Auth auth) {
		return authDao.deleteAuth(auth);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateAuth(Auth auth) {
		return authDao.updateAuth(auth);
	}

	@Override
	public Auth getAuth(Auth auth) {
		return authDao.getAuth(auth);
	}

	@Override
	public List<Auth> getAuthList(Map<String, Object> param) {
		return authDao.getAuthList(param);
	}

	@Override
	public int getAuthListCount(Map<String, Object> param) {
		return authDao.getAuthListCount(param);
	}

	@Override
	public int addModule(Module module) {
		return moduleDao.addModule(module);
	}

	@Override
	public int deleteModule(Module module) {
		return moduleDao.deleteModule(module);
	}

	@Override
	public int updateModule(Module module) {
		return moduleDao.updateModule(module);
	}

	@Override
	public Module getModule(Module module) {
		return moduleDao.getModule(module);
	}

	@Override
	public List<Module> getModuleList(Map<String, Object> param) {
		return moduleDao.getModuleList(param);
	}

	@Override
	public int getModuleListCount(Map<String, Object> param) {
		return moduleDao.getModuleListCount(param);
	}

	@Override
	public int addThirdPartRoomStatus(List<ThirdPartRoomStatus> list) {
		if(list==null||list.isEmpty()){
			return 0;
		}
		Map<String,Object>param=new HashMap<String,Object>();
		int c=0;
		for(ThirdPartRoomStatus roomStatus:list){
			thirdPartRoomStatusDao.addThirdPartRoomStatus(roomStatus);
			//如果系统房源关联了第三方房源，则自动给系统的房态插入信息
			String thirdPartId=roomStatus.getThirdPartId();
			param.clear();
			param.put("thirdPartId", thirdPartId);
			List<House>houses=houseDao.getHouseList(param);
			if(houses!=null&&!houses.isEmpty()){
				House house=houses.get(0);
				
				//HouseDayStatus houseDayStatus=new HouseDayStatus();
				//houseDayStatus.setHouseId(house.getId());
				//houseDayStatus.setDate(roomStatus.getDate());
				//houseDayStatus.setStatus(roomStatus.getStatus());
				//先查询是否有了房态，有则更新，无则新增
				Map<String,Object>map=new HashMap<String,Object>();
				map.put("houseId", house.getId());
				map.put("date", roomStatus.getDate());
				Map<String,Object>ret=houseDayStatusDao.getHouseStatus(map);
				if(ret!=null&&!ret.isEmpty()){
					//更新，标记了无房，和有了订单，则不能更新状态了
					Integer id=(Integer) ret.get("id");
					Integer status=(Integer) ret.get("status");
					Integer orderId=(Integer) ret.get("orderId");
					if(status!=null&&status.intValue()==2){
						//已经标记为无房，不能修改
					}else if(orderId!=null){
						//已经存在订单，不能自动更新状态
					}else{
						//可以自动更新状态
						HouseDayStatus updateItem=new HouseDayStatus();
						updateItem.setId(id);
						updateItem.setStatus(roomStatus.getStatus());
						houseDayStatusDao.updateHouseDayStatus(updateItem);
					}
				}else{
					//新增
					HouseDayStatus houseDayStatus=new HouseDayStatus();
					houseDayStatus.setHouseId(house.getId());
					houseDayStatus.setDate(roomStatus.getDate());
					houseDayStatus.setStatus(roomStatus.getStatus());
					houseDayStatusDao.addHouseDayStatus(houseDayStatus);
				}
				
			}
			c++;
		}
		
		return c;
	}

	@Override
	public List<Map<String, Object>> getThirdPartHouseList(Map<String, Object> param) {
		return thirdPartRoomStatusDao.getThirdPartHouseList(param);
	}

	@Override
	public List<Map<String,Object>> getDistrictHouseList(Map<String, Object> param) {
		return districtDao.getDistrictHouseList(param);
	}

	@Override
	public int getDistrictHouseListCount(Map<String, Object> param) {
		return districtDao.getDistrictHouseListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addDistrictImg(DistrictImg districtImg) {
		return districtDao.addDistrictImg(districtImg);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteDistrictImg(DistrictImg districtImg) {
		return districtDao.deleteDistrictImg(districtImg);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateDistrictImg(DistrictImg districtImg) {
		return districtDao.updateDistrictImg(districtImg);
	}

	@Override
	public DistrictImg getDistrictImg(DistrictImg districtImg) {
		return districtDao.getDistrictImg(districtImg);
	}

	@Override
	public List<DistrictImg> getDistrictImgList(Map<String, Object> param) {
		return districtDao.getDistrictImgList(param);
	}

	@Override
	public int getDistrictImgListCount(Map<String, Object> param) {
		return districtDao.getDistrictImgListCount(param);
	}

}
