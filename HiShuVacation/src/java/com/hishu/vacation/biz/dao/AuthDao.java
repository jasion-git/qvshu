package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Auth;

public interface AuthDao {

	public int addAuth(Auth auth);
	public int deleteAuth(Auth auth);
	public int updateAuth(Auth auth);
	public Auth getAuth(Auth auth);
	public List<Auth>getAuthList(Map<String,Object>param);
	public int getAuthListCount(Map<String,Object>param);
}
