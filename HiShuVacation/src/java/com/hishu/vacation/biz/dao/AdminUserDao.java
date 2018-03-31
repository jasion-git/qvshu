package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.AdminUser;

public interface AdminUserDao {

	public int addAdminUser(AdminUser adminUser);
	public int deleteAdminUser(AdminUser adminUser);
	public int updateAdminUser(AdminUser adminUser);
	public AdminUser getAdminUser(AdminUser adminUser);
	public List<AdminUser>getAdminUserList(Map<String,Object>param);
	public int getAdminUserListCount(Map<String,Object>param);
}
