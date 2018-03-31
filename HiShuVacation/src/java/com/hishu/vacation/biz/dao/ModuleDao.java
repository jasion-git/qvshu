package com.hishu.vacation.biz.dao;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Module;

public interface ModuleDao {

	public int addModule(Module module);
	public int deleteModule(Module module);
	public int updateModule(Module module);
	public Module getModule(Module module);
	public List<Module>getModuleList(Map<String,Object>param);
	public int getModuleListCount(Map<String,Object>param);
}
