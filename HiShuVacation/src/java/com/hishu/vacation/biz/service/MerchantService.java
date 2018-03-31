package com.hishu.vacation.biz.service;

import java.util.List;
import java.util.Map;

import com.hishu.vacation.dto.Merchant;
import com.hishu.vacation.dto.MerchantContact;

public interface MerchantService {

	public int addMerchant(Merchant merchant);
	public int deleteMerchant(Merchant merchant);
	public int updateMerchant(Merchant merchant);
	public Merchant getMerchant(Merchant merchant);
	public List<Merchant>getMerchantList(Map<String,Object>param);
	public int getMerchantListCount(Map<String,Object>param);
	
	public int addMerchantContact(MerchantContact merchantContat);
	public int batchDeleteMerchantContact(MerchantContact merchantContat);
	public List<MerchantContact>getMerchantContactList(Map<String,Object>param);
}
