package com.hishu.vacation.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hishu.vacation.biz.dao.BedDao;
import com.hishu.vacation.biz.dao.HouseDao;
import com.hishu.vacation.biz.dao.HouseDayPriceDao;
import com.hishu.vacation.biz.dao.HouseImgDao;
import com.hishu.vacation.biz.service.HouseService;
import com.hishu.vacation.dto.Bed;
import com.hishu.vacation.dto.House;
import com.hishu.vacation.dto.HouseAttribute;
import com.hishu.vacation.dto.HouseDayPrice;
import com.hishu.vacation.dto.HouseFacility;
import com.hishu.vacation.dto.HouseImg;
import com.hishu.vacation.dto.HouseReception;
import com.hishu.vacation.exception.BizException;
import com.hishu.vacation.exception.ReturnCode;

@Service("houseService")
@Transactional
public class HouseServiceImpl implements HouseService {

	@Autowired
	private HouseDao houseDao;
	@Autowired
	private HouseImgDao houseImgDao;
	@Autowired
	private HouseDayPriceDao houseDayPriceDao;
	@Autowired
	private BedDao bedDao;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouse(House house) {
		int c=houseDao.addHouse(house);
		if(c>0&&house.getId()!=null){
			//添加床数据
			String houseBeds=house.getHouseBeds();
			if(StringUtils.isNotEmpty(houseBeds)){
				String[]items=houseBeds.split(";");
				if(items!=null){
					for(String s:items){
						//bed1_width+","+bed1_length+","+bed1_count+","+bed1_type;
						String[]ss=s.split(",");
						if(ss.length!=4){
							//床的数据不对，不添加
							continue;
						}
						Bed bed=new Bed();
						bed.setWidth(Double.parseDouble(ss[0]));
						bed.setLength(Double.parseDouble(ss[1]));
						bed.setCount(Integer.parseInt(ss[2]));
						bed.setMattress(ss[3]);
						bed.setHouseId(house.getId());
						bedDao.addBed(bed);
					}
				}
			}
			
			//添加房源属性
			String houseAttributes=house.getHouseAttributes();
			if(StringUtils.isNotEmpty(houseAttributes)){
				String[]items=houseAttributes.split(",");
				List<String>list=Arrays.asList(items);
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("houseId", house.getId());
				param.put("list", list);
				houseDao.batchAddHouseAttributeRef(param);
			}
			
			//添加房源设施
			String houseFacilities=house.getHouseFacilities();
			if(StringUtils.isNotEmpty(houseFacilities)){
				String[]items=houseFacilities.split(",");
				List<String>list=Arrays.asList(items);
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("houseId", house.getId());
				param.put("list", list);
				houseDao.bathAddHouseFacilityRef(param);
			}
			
			//添加房源接待
			String houseReceptions=house.getHouseReceptions();
			if(StringUtils.isNotEmpty(houseReceptions)){
				String[]items=houseReceptions.split(",");
				List<String>list=Arrays.asList(items);
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("houseId", house.getId());
				param.put("list", list);
				houseDao.bathAddHouseReceptionRef(param);
			}
			
		}
		
		return c;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouse(House house) {
		return houseDao.deleteHouse(house);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouse(House house) {
		//添加床数据
		String houseBeds=house.getHouseBeds();
		if(StringUtils.isNotEmpty(houseBeds)){
			//先删除，后插入
			Bed batchBed=new Bed();
			batchBed.setHouseId(house.getId());
			bedDao.bathDeleteBed(batchBed);
			
			String[]items=houseBeds.split(";");
			if(items!=null){
				for(String s:items){
					//bed1_width+","+bed1_length+","+bed1_count+","+bed1_type;
					String[]ss=s.split(",");
					if(ss.length!=4){
						//床的数据不对，不添加
						continue;
					}
					Bed bed=new Bed();
					bed.setWidth(Double.parseDouble(ss[0]));
					bed.setLength(Double.parseDouble(ss[1]));
					bed.setCount(Integer.parseInt(ss[2]));
					bed.setMattress(ss[3]);
					bed.setHouseId(house.getId());
					bedDao.addBed(bed);
				}
			}
		}
		
		Map<String,Object>para=new HashMap<String,Object>();
		para.put("houseId", house.getId());
		//添加房源属性
		String houseAttributes=house.getHouseAttributes();
		if(houseAttributes!=null){
			//先删后插
			houseDao.bathDeleteHouseAttributeRef(para);
			
			if(!"".equals(houseAttributes)){
				String[]items=houseAttributes.split(",");
				List<String>list=Arrays.asList(items);
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("houseId", house.getId());
				param.put("list", list);
				houseDao.batchAddHouseAttributeRef(param);
			}
		}
		
		//添加房源设施
		String houseFacilities=house.getHouseFacilities();
		if(houseFacilities!=null){
			//先删后插
			houseDao.bathDeleteHouseFacilityRef(para);
			
			if(!"".equals(houseFacilities)){
				String[]items=houseFacilities.split(",");
				List<String>list=Arrays.asList(items);
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("houseId", house.getId());
				param.put("list", list);
				houseDao.bathAddHouseFacilityRef(param);
			}
		}
		
		//添加房源接待
		String houseReceptions=house.getHouseReceptions();
		if(houseReceptions!=null){
			//先删后插
			houseDao.bathDeleteHouseReceptionRef(para);
			
			if(!"".equals(houseReceptions)){
				String[]items=houseReceptions.split(",");
				List<String>list=Arrays.asList(items);
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("houseId", house.getId());
				param.put("list", list);
				houseDao.bathAddHouseReceptionRef(param);
			}
			
		}
		
		return houseDao.updateHouse(house);
	}

	@Override
	public House getHouse(House house) {
		House houseInDB=houseDao.getHouse(house);
		//填充其他数据
		//加载图片
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("houseId", house.getId());
		List<HouseImg>houseImgList=houseImgDao.getHouseImgList(param);
		houseInDB.setHouseImgList(houseImgList);
		
		//填充床铺信息
		List<Bed>bedList=bedDao.getBedList(param);
		houseInDB.setBedList(bedList);
		
		return houseInDB;
	}

	@Override
	public List<House> getHouseList(Map<String, Object> param) {
		return houseDao.getHouseList(param);
	}

	@Override
	public int getHouseListCount(Map<String, Object> param) {
		return houseDao.getHouseListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouseImg(HouseImg img) {
		return houseImgDao.addHouseImg(img);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseImg(HouseImg img) {
		return houseImgDao.deleteHouseImg(img);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseImgGroup(HouseImg img) {
		return houseImgDao.deleteHouseImgGroup(img);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouseImg(HouseImg img) {
		return houseImgDao.updateHouseImg(img);
	}

	@Override
	public HouseImg getHouseImg(HouseImg img) {
		return houseImgDao.getHouseImg(img);
	}

	@Override
	public List<HouseImg> getHouseImgList(Map<String, Object> param) {
		return houseImgDao.getHouseImgList(param);
	}

	@Override
	public int getHouseImgListCount(Map<String, Object> param) {
		return houseImgDao.getHouseImgListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouseDayPrice(HouseDayPrice houseDayPrice) {
		return houseDayPriceDao.addHouseDayPrice(houseDayPrice);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseDayPrice(HouseDayPrice houseDayPrice) {
		return houseDayPriceDao.deleteHouseDayPrice(houseDayPrice);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouseDayPrice(HouseDayPrice houseDayPrice) {
		return houseDayPriceDao.updateHouseDayPrice(houseDayPrice);
	}

	@Override
	public HouseDayPrice getHouseDayPrice(HouseDayPrice houseDayPrice) {
		return houseDayPriceDao.getHouseDayPrice(houseDayPrice);
	}

	@Override
	public List<HouseDayPrice> getHouseDayPriceList(Map<String, Object> param) {
		return houseDayPriceDao.getHouseDayPriceList(param);
	}

	@Override
	public int getHouseDayPriceListCount(Map<String, Object> param) {
		return houseDayPriceDao.getHouseDayPriceListCount(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouseAttribute(HouseAttribute houseAttribute) {
		return houseDao.addHouseAttribute(houseAttribute);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseAttribute(HouseAttribute houseAttribute) {
		return houseDao.deleteHouseAttribute(houseAttribute);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouseAttribute(HouseAttribute houseAttribute) {
		return houseDao.updateHouseAttribute(houseAttribute);
	}

	@Override
	public HouseAttribute getHouseAttribute(HouseAttribute houseAttribute) {
		return houseDao.getHouseAttribute(houseAttribute);
	}

	@Override
	public List<HouseAttribute> getHouseAttributeList(Map<String, Object> param) {
		return houseDao.getHouseAttributeList(param);
	}

	@Override
	public int getHouseAttributeListCount(Map<String, Object> param) {
		return houseDao.getHouseAttributeListCount(param);
	}

	@Override
	public List<HouseAttribute> getHouseAttributeListOfHouse(
			Map<String, Object> param) {
		return houseDao.getHouseAttributeListOfHouse(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouseFacility(HouseFacility houseFacility) {
		return houseDao.addHouseFacility(houseFacility);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseFacility(HouseFacility houseFacility) {
		return houseDao.deleteHouseFacility(houseFacility);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouseFacility(HouseFacility houseFacility) {
		return houseDao.updateHouseFacility(houseFacility);
	}

	@Override
	public HouseFacility getHouseFacility(HouseFacility houseFacility) {
		return houseDao.getHouseFacility(houseFacility);
	}

	@Override
	public List<HouseFacility> getHouseFacilityList(Map<String, Object> param) {
		return houseDao.getHouseFacilityList(param);
	}

	@Override
	public int getHouseFacilityListCount(Map<String, Object> param) {
		return houseDao.getHouseFacilityListCount(param);
	}

	@Override
	public List<HouseFacility> getHouseFacilityOfHouse(Map<String, Object> param) {
		return houseDao.getHouseFacilityOfHouse(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int addHouseReception(HouseReception houseReceptioin) {
		return houseDao.addHouseReception(houseReceptioin);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteHouseReception(HouseReception houseReceptioin) {
		return houseDao.deleteHouseReception(houseReceptioin);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateHouseReception(HouseReception houseReceptioin) {
		return houseDao.updateHouseReception(houseReceptioin);
	}

	@Override
	public HouseReception getHouseReception(HouseReception houseReceptioin) {
		return houseDao.getHouseReception(houseReceptioin);
	}

	@Override
	public List<HouseReception> getHouseReceptionList(Map<String, Object> param) {
		return houseDao.getHouseReceptionList(param);
	}

	@Override
	public int getHouseReceptionListCount(Map<String, Object> param) {
		return houseDao.getHouseReceptionListCount(param);
	}

	@Override
	public List<HouseReception> getHouseReceptionOfHouse(
			Map<String, Object> param) {
		return houseDao.getHouseReceptionOfHouse(param);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int editHouseDayPrice(HouseDayPrice houseDayPrice) {
		//先判断是否存在该房价
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("houseId", houseDayPrice.getHouseId());
		param.put("day", houseDayPrice.getDay());
		List<HouseDayPrice>list=houseDayPriceDao.getHouseDayPriceList(param);
		if(list==null||list.isEmpty()){
			//不存在，则新增
			if(houseDayPrice.getOrderAmount()==null&&houseDayPrice.getCollectionAmount()==null){
				return 0;
			}
			return houseDayPriceDao.addHouseDayPrice(houseDayPrice);
		}else{
			//存在，则修改
			HouseDayPrice houseDayPriceInDB=list.get(0);
			if(houseDayPrice.getOrderAmount()==null&&houseDayPrice.getCollectionAmount()==null){
				return 0;
			}
			houseDayPrice.setId(houseDayPriceInDB.getId());
			return houseDayPriceDao.updateHouseDayPrice(houseDayPrice);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int editHouseDayPriceList(List<HouseDayPrice> houseDayPriceList) throws Exception{
		if(houseDayPriceList==null||houseDayPriceList.isEmpty()){
			return 0;
		}
		
		for(HouseDayPrice houseDayPrice:houseDayPriceList){
			if(houseDayPrice.getHouseId()==null){
				throw new BizException(ReturnCode.ERROR_PARAM_IS_REQUIRED,"houseId");
			}
			if(houseDayPrice.getDay()==null){
				throw new BizException(ReturnCode.ERROR_PARAM_IS_REQUIRED,"day");
			}
			editHouseDayPrice(houseDayPrice);
		}
		return 1;
	}

	@Override
	public List<Map<String, Object>> getHouseListOfBeOverdue(
			Map<String, Object> param) {
		return houseDao.getHouseListOfBeOverdue(param);
	}

	@Override
	public List<Map<String, Object>> getHouseListOfWillBeOverdue(
			Map<String, Object> param) {
		//计算出未来30天的时间
		Calendar c=Calendar.getInstance();
		Date now=c.getTime();
		c.add(Calendar.MONTH, 30);
		Date day30=c.getTime();
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String startTime=df.format(now);
		String endTime=df.format(day30);
		
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		
		return houseDao.getHouseListOfWillBeOverdue(param);
	}
}
