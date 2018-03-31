package com.hishu.vacation.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hishu.vacation.biz.dao.MerchantDao;
import com.hishu.vacation.biz.service.MerchantService;
import com.hishu.vacation.dto.Merchant;
import com.hishu.vacation.dto.MerchantContact;

@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addMerchant(Merchant merchant) {
		int c=merchantDao.addMerchant(merchant);
		if(merchant.getId()!=null){
			//添加联系人
			String merchantContactList=merchant.getMerchantContactList();
			if(StringUtils.isNotEmpty(merchantContactList)){
				//type,name,phone,district;type,name,phone,district
				String[]items=merchantContactList.split(";");
				if(items!=null&&items.length>0){
					for(String m:items){
						String[]ii=m.split(",");
						if(ii==null||ii.length==0){
							continue;
						}
						Integer type=Integer.parseInt(ii[0]);
						String name=ii[1];
						String phone=ii[2];
						Integer districtId=null;
						if(ii.length>3){
							districtId=StringUtils.isEmpty(ii[3])?null:Integer.parseInt(ii[3]);
						}
						MerchantContact merchantContact=new MerchantContact();
						merchantContact.setMerchantId(merchant.getId());
						merchantContact.setType(type);
						merchantContact.setName(name);
						merchantContact.setPhone(phone);
						merchantContact.setDistrictId(districtId);
						merchantDao.addMerchantContact(merchantContact);
					}
				}
			}
			
		}
		return c;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteMerchant(Merchant merchant) {
		return merchantDao.deleteMerchant(merchant);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateMerchant(Merchant merchant) {
		int c=merchantDao.updateMerchant(merchant);
		//添加联系人
		String merchantContactList=merchant.getMerchantContactList();
		if(StringUtils.isNotEmpty(merchantContactList)){
			//先删除
			MerchantContact me=new MerchantContact();
			me.setMerchantId(merchant.getId());
			merchantDao.batchDeleteMerchantContact(me);
			//type,name,phone,district;type,name,phone,district
			String[]items=merchantContactList.split(";");
			if(items!=null&&items.length>0){
				for(String m:items){
					String[]ii=m.split(",");
					Integer type=Integer.parseInt(ii[0]);
					String name=ii[1];
					String phone=ii[2];
					Integer districtId=null;
					if(ii.length>3){
						districtId=StringUtils.isEmpty(ii[3])?null:Integer.parseInt(ii[3]);
					}
					MerchantContact merchantContact=new MerchantContact();
					merchantContact.setMerchantId(merchant.getId());
					merchantContact.setType(type);
					merchantContact.setName(name);
					merchantContact.setPhone(phone);
					merchantContact.setDistrictId(districtId);
					merchantDao.addMerchantContact(merchantContact);
				}
			}
		}
		
		return c;
	}

	@Override
	public Merchant getMerchant(Merchant merchant) {
		Merchant merchantInDB=merchantDao.getMerchant(merchant);
		if(merchantInDB!=null){
			//加载联系人
			Map<String,Object>param=new HashMap<String,Object>();
			param.put("merchantId", merchantInDB.getId());
			List<MerchantContact>merchantContacts=merchantDao.getMerchantContactList(param);
			merchantInDB.setMerchantContacts(merchantContacts);
		}
		return merchantInDB;
	}

	@Override
	public List<Merchant> getMerchantList(Map<String, Object> param) {
		return merchantDao.getMerchantList(param);
	}

	@Override
	public int getMerchantListCount(Map<String, Object> param) {
		return merchantDao.getMerchantListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addMerchantContact(MerchantContact merchantContat) {
		return merchantDao.addMerchantContact(merchantContat);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int batchDeleteMerchantContact(MerchantContact merchantContat) {
		return merchantDao.batchDeleteMerchantContact(merchantContat);
	}

	@Override
	public List<MerchantContact> getMerchantContactList(
			Map<String, Object> param) {
		return merchantDao.getMerchantContactList(param);
	}

}
