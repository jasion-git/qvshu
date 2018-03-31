package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Auth;
import com.hishu.vacation.dto.Module;
import com.hishu.vacation.dto.Role;

public interface RoleDao {

	public int addRole(Role role);
	public int deleteRole(Role role);
	public int updateRole(Role role);
	public Role getRole(Role role);
	public List<Role>getRoleList(Map<String,Object>param);
	public int getRoleListCount(Map<String,Object>param);
	
	public List<Module>getModuleListByRoleId(Map<String,Object>param);
	public List<Auth>getAuthListByRoleId(Map<String,Object>param);
}
