package com.hishu.vacation.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hishu.vacation.annotation.Log;
import com.hishu.vacation.annotation.ValidateFiled;
import com.hishu.vacation.annotation.ValidateGroup;
import com.hishu.vacation.biz.file.FileService;
import com.hishu.vacation.biz.file.FileServiceFactory;
import com.hishu.vacation.biz.service.ActionService;
import com.hishu.vacation.biz.service.AdminUserService;
import com.hishu.vacation.biz.service.HouseService;
import com.hishu.vacation.biz.service.MerchantService;
import com.hishu.vacation.dto.City;
import com.hishu.vacation.dto.CommonParam;
import com.hishu.vacation.dto.CouponPackage;
import com.hishu.vacation.dto.District;
import com.hishu.vacation.dto.DistrictImg;
import com.hishu.vacation.dto.House;
import com.hishu.vacation.dto.HouseAttribute;
import com.hishu.vacation.dto.HouseDayPrice;
import com.hishu.vacation.dto.HouseFacility;
import com.hishu.vacation.dto.HouseImg;
import com.hishu.vacation.dto.HouseReception;
import com.hishu.vacation.dto.JsonResult;
import com.hishu.vacation.dto.Merchant;
import com.hishu.vacation.dto.Provice;

@Controller
@RequestMapping("/admin")
public class AdminController {

	protected final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private HouseService houseService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private AdminUserService adminUserService;
	
	//*************************************房源管理  开始*****************************************
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="merchantId"),
            @ValidateFiled(index=0,notNull=true,filedName="districtId"),
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="type"),
            @ValidateFiled(index=0,filedName="shareImgUrl"),
            @ValidateFiled(index=0,filedName="shareContent"),
            @ValidateFiled(index=0,filedName="shareTitle"),
            @ValidateFiled(index=0,filedName="funCount"),
            @ValidateFiled(index=0,filedName="liveCount"),
            @ValidateFiled(index=0,filedName="parking"),
            @ValidateFiled(index=0,filedName="isSpotCheck"),
            @ValidateFiled(index=0,filedName="toiletIndependen"),
            @ValidateFiled(index=0,filedName="toiletCommon"),
            @ValidateFiled(index=0,filedName="layoutBalcony"),
            @ValidateFiled(index=0,filedName="layoutKitchen"),
            @ValidateFiled(index=0,filedName="layoutOffice"),
            @ValidateFiled(index=0,filedName="layoutRoom"),
            @ValidateFiled(index=0,filedName="floorCount"),
            @ValidateFiled(index=0,filedName="area"),
            @ValidateFiled(index=0,filedName="similarHouseCount"),
            @ValidateFiled(index=0,filedName="manageType"),
            @ValidateFiled(index=0,filedName="introduction"),
            @ValidateFiled(index=0,filedName="subIntroduction"),
            @ValidateFiled(index=0,filedName="offlineNo")
    })
	@Log
	@RequestMapping(value="/addHouse.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addHouse(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		int c=houseService.addHouse(house);
		if(c>0){
			//需要带上路径
			Integer id=house.getId();
			
			FileServiceFactory factory=FileServiceFactory.getInstance();
			FileService fileService=factory.getFileService("local");
			
			MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
			MultipartFile img1=mulRequest.getFile("shareImg");
			if(img1!=null){
				//上传文件
				String oldFileName=img1.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="house"+id+"/"+fileName;
				String url=fileService.uploadFile(img1.getBytes(), fileName, "123456789");
				/*HouseImg img=new HouseImg();
				img.setHouseId(id);
				img.setType("common");
				img.setUrl(url);
				houseService.addHouseImg(img);*/
				House houseUpdate=new House();
				houseUpdate.setId(id);
				houseUpdate.setShareImgUrl(url);
				houseService.updateHouse(houseUpdate);
			}
			result.setCode(JsonResult.CODE_SUCCESS);
		}else{
			result.setCode(JsonResult.CODE_FAIL);
		}
		Map<String,Object>houseMap=new HashMap<String,Object>();
		houseMap.put("id", house.getId());
		result.setData(houseMap);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/uploadHouseImgs.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult uploadHouseImgs(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		//需要带上路径
		Integer id=house.getId();
		String imgCountStr=request.getParameter("imgCount");
		Integer imgCount=Integer.parseInt(imgCountStr);
		if(imgCount==null||imgCount.intValue()==0){
			result.setCode(JsonResult.CODE_SUCCESS);
			return result;
		}
		
		FileServiceFactory factory=FileServiceFactory.getInstance();
		FileService fileService=factory.getFileService("local");
		
		MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
		for(int i=0;i<imgCount;i++){
			MultipartFile img1=mulRequest.getFile("img"+i);
			if(img1!=null){
				//上传文件
				String oldFileName=img1.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="house"+id+"/"+fileName;
				String url=fileService.uploadFile(img1.getBytes(), fileName, "123456789");
				HouseImg img=new HouseImg();
				img.setHouseId(id);
				img.setType("common");
				img.setUrl(url);
				houseService.addHouseImg(img);
				
			}
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteHouseImg.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteHouseImg(HouseImg houseImg,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		houseService.deleteHouseImg(houseImg);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteHouse.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteHouse(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		int c=houseService.deleteHouse(house);
		if(c>0){
			//删除房源的图片文件
			FileServiceFactory factory=FileServiceFactory.getInstance();
			FileService fileService=factory.getFileService("local");
			
			String imgGroup="house"+house.getId();
			fileService.removeFileGroup(imgGroup, "123456789");
			
			result.setCode(JsonResult.CODE_SUCCESS);
		}else{
			result.setCode(JsonResult.CODE_FAIL);
		}
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="merchantId"),
            @ValidateFiled(index=0,filedName="districtId"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="type"),
            @ValidateFiled(index=0,filedName="shareImgUrl"),
            @ValidateFiled(index=0,filedName="shareContent"),
            @ValidateFiled(index=0,filedName="shareTitle"),
            @ValidateFiled(index=0,filedName="funCount"),
            @ValidateFiled(index=0,filedName="liveCount"),
            @ValidateFiled(index=0,filedName="parking"),
            @ValidateFiled(index=0,filedName="isSpotCheck"),
            @ValidateFiled(index=0,filedName="toiletIndependen"),
            @ValidateFiled(index=0,filedName="toiletCommon"),
            @ValidateFiled(index=0,filedName="layoutBalcony"),
            @ValidateFiled(index=0,filedName="layoutKitchen"),
            @ValidateFiled(index=0,filedName="layoutOffice"),
            @ValidateFiled(index=0,filedName="layoutRoom"),
            @ValidateFiled(index=0,filedName="floorCount"),
            @ValidateFiled(index=0,filedName="area"),
            @ValidateFiled(index=0,filedName="similarHouseCount"),
            @ValidateFiled(index=0,filedName="manageType"),
            @ValidateFiled(index=0,filedName="introduction"),
            @ValidateFiled(index=0,filedName="subIntroduction"),
            @ValidateFiled(index=0,filedName="offlineNo")
    })
	@Log
	@RequestMapping(value="/updateHouse.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateHouse(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		houseService.updateHouse(house);
		FileServiceFactory factory=FileServiceFactory.getInstance();
		FileService fileService=factory.getFileService("local");
		Integer id=house.getId();
		MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
		MultipartFile img1=mulRequest.getFile("shareImg");
		if(img1!=null){
			//上传文件
			String oldFileName=img1.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="house"+id+"/"+fileName;
			String url=fileService.uploadFile(img1.getBytes(), fileName, "123456789");
			
			House houseUpdate=new House();
			houseUpdate.setId(id);
			houseUpdate.setShareImgUrl(url);
			houseService.updateHouse(houseUpdate);
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getHouse.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouse(House house,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		House houseInDB=houseService.getHouse(house);
		
		result.setData(houseInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	
	@RequestMapping(value="/getHouseList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String pageNumStr=request.getParameter("pageNum");
		String pageSizeStr=request.getParameter("pageSize");
		Integer pageNum=1;
		if(StringUtils.isNotEmpty(pageNumStr)){
			pageNum=Integer.parseInt(pageNumStr);
		}
		Integer pageSize=20;
		if(StringUtils.isNotEmpty(pageSizeStr)){
			pageSize=Integer.parseInt(pageSizeStr);
		}
		Integer offset=(pageNum-1)*pageSize;
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("pageSize", pageSize);
		param.put("offset", offset);
		String likeName=request.getParameter("likeName");
		if(StringUtils.isNotEmpty(likeName)){
			param.put("likeName", likeName);
		}
		String cityIdStr=request.getParameter("cityId");
		if(StringUtils.isNotEmpty(cityIdStr)){
			param.put("cityId", cityIdStr);
		}
		String districtIdStr=request.getParameter("districtId");
		if(StringUtils.isNotEmpty(districtIdStr)){
			param.put("districtId", districtIdStr);
		}
		String merchantIdStr=request.getParameter("merchantId");
		if(StringUtils.isNotEmpty(merchantIdStr)){
			param.put("merchantId", merchantIdStr);
		}
		String layoutRoomStr=request.getParameter("layoutRoom");
		Integer layoutRoom=StringUtils.isEmpty(layoutRoomStr)?null:Integer.parseInt(layoutRoomStr);
		param.put("layoutRoom", layoutRoom);
		String liveCountStr=request.getParameter("liveCount");
		Integer liveCount=StringUtils.isEmpty(liveCountStr)?null:Integer.parseInt(liveCountStr);
		param.put("liveCount", liveCount);
		
		List<House>list=houseService.getHouseList(param);
		int total=houseService.getHouseListCount(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	//*************************************房源管理  结束*****************************************
	
	//*************************************商户管理  开始*****************************************
	//*************************************房源管理  开始*****************************************
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="account"),
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="sex"),
            @ValidateFiled(index=0,notNull=true,filedName="cancelBookType"),
            @ValidateFiled(index=0,filedName="provinceId"),
            @ValidateFiled(index=0,filedName="cityId"),
            @ValidateFiled(index=0,filedName="districtId"),
            @ValidateFiled(index=0,filedName="cancelBookDes"),
            @ValidateFiled(index=0,filedName="bookInstruction"),
            @ValidateFiled(index=0,filedName="bankRemark"),
            @ValidateFiled(index=0,filedName="bankSettlement"),
            @ValidateFiled(index=0,filedName="bankAccount"),
            @ValidateFiled(index=0,filedName="bankSubBank"),
            @ValidateFiled(index=0,filedName="bankProvince"),
            @ValidateFiled(index=0,filedName="bankPhone"),
            @ValidateFiled(index=0,filedName="bankName")
    })
	@Log
	@RequestMapping(value="/addMerchant.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addMerchant(Merchant merchant,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		int c=merchantService.addMerchant(merchant);
		
		if(c>0){
			result.setCode(JsonResult.CODE_SUCCESS);
		}else{
			result.setCode(JsonResult.CODE_FAIL);
		}
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteMerchant.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteMerchant(Merchant merchant,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		int c=merchantService.deleteMerchant(merchant);
		
		if(c>0){
			result.setCode(JsonResult.CODE_SUCCESS);
		}else{
			result.setCode(JsonResult.CODE_FAIL);
		}
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="sex"),
            @ValidateFiled(index=0,filedName="cancelBookType"),
            @ValidateFiled(index=0,filedName="provinceId"),
            @ValidateFiled(index=0,filedName="cityId"),
            @ValidateFiled(index=0,filedName="districtId"),
            @ValidateFiled(index=0,filedName="cancelBookDes"),
            @ValidateFiled(index=0,filedName="bookInstruction"),
            @ValidateFiled(index=0,filedName="bankRemark"),
            @ValidateFiled(index=0,filedName="bankSettlement"),
            @ValidateFiled(index=0,filedName="bankAccount"),
            @ValidateFiled(index=0,filedName="bankSubBank"),
            @ValidateFiled(index=0,filedName="bankProvince"),
            @ValidateFiled(index=0,filedName="bankPhone"),
            @ValidateFiled(index=0,filedName="bankName")
    })
	@Log
	@RequestMapping(value="/updateMerchant.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateMerchant(Merchant merchant,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		merchantService.updateMerchant(merchant);
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getMerchant.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getMerchant(Merchant merchant,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Merchant mercahntInDB=merchantService.getMerchant(merchant);
		
		result.setData(mercahntInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getMerchantList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getMerchantList(Merchant merchant,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String pageNumStr=request.getParameter("pageNum");
		String pageSizeStr=request.getParameter("pageSize");
		Integer pageNum=1;
		if(StringUtils.isNotEmpty(pageNumStr)){
			pageNum=Integer.parseInt(pageNumStr);
		}
		Integer pageSize=20;
		if(StringUtils.isNotEmpty(pageSizeStr)){
			pageSize=Integer.parseInt(pageSizeStr);
		}
		Integer offset=(pageNum-1)*pageSize;
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("pageSize", pageSize);
		param.put("offset", offset);
		String likeName=request.getParameter("likeName");
		if(StringUtils.isNotEmpty(likeName)){
			param.put("likeName", likeName);
		}
		String cityIdStr=request.getParameter("cityId");
		if(StringUtils.isNotEmpty(cityIdStr)){
			param.put("cityId", cityIdStr);
		}
		String districtIdStr=request.getParameter("districtId");
		if(StringUtils.isNotEmpty(districtIdStr)){
			param.put("districtId", districtIdStr);
		}
		List<Merchant>list=merchantService.getMerchantList(param);
		int total=merchantService.getMerchantListCount(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	//*************************************商户管理  结束*****************************************
	
	//*************************************活动管理  开始*****************************************
	@RequestMapping(value="/getActionList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getActionList(Merchant merchant,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String pageNumStr=request.getParameter("pageNum");
		String pageSizeStr=request.getParameter("pageSize");
		Integer pageNum=1;
		if(StringUtils.isNotEmpty(pageNumStr)){
			pageNum=Integer.parseInt(pageNumStr);
		}
		Integer pageSize=20;
		if(StringUtils.isNotEmpty(pageSizeStr)){
			pageSize=Integer.parseInt(pageSizeStr);
		}
		Integer offset=(pageNum-1)*pageSize;
		
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("pageSize", pageSize);
		param.put("offset", offset);
		
		List<CouponPackage>list=actionService.getCouponPackageList(param);
		int total=actionService.getCouponPackageListCount(param);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		result.setData(ret);
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="type"),
            @ValidateFiled(index=0,filedName="houseIds"),
            @ValidateFiled(index=0,filedName="content"),
            @ValidateFiled(index=0,filedName="startTime"),
            @ValidateFiled(index=0,filedName="endTime"),
            @ValidateFiled(index=0,filedName="count"),
            @ValidateFiled(index=0,filedName="condition"),
            @ValidateFiled(index=0,filedName="conditionValue"),
            @ValidateFiled(index=0,filedName="discount")
    })
	@Log
	@RequestMapping(value="/addAction.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addAction(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		actionService.addCouponPackage(couponPackage);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteAction.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteAction(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		actionService.deleteCouponPackage(couponPackage);
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/getAction.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getAction(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		CouponPackage couponPackageInDB=actionService.getCouponPackage(couponPackage);
		result.setData(couponPackageInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="type"),
            @ValidateFiled(index=0,filedName="houseIds"),
            @ValidateFiled(index=0,filedName="content"),
            @ValidateFiled(index=0,filedName="startTime"),
            @ValidateFiled(index=0,filedName="endTime"),
            @ValidateFiled(index=0,filedName="count"),
            @ValidateFiled(index=0,filedName="condition"),
            @ValidateFiled(index=0,filedName="conditionValue"),
            @ValidateFiled(index=0,filedName="discount")
    })
	@Log
	@RequestMapping(value="/updateAction.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateAction(CouponPackage couponPackage,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		actionService.updateCouponPackage(couponPackage);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	//*************************************活动管理  结束*****************************************
	
	//*************************************活动管理  开始*****************************************
	
	@RequestMapping(value="/getThirdPartHouseList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getThirdPartHouseList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		
		Map<String,Object>ret=new HashMap<String,Object>();
		
		Map<String,Object>param=new HashMap<String,Object>();
		List<Map<String,Object>>list=adminUserService.getThirdPartHouseList(param);
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	//*************************************省份管理  开始*****************************************
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="code"),
            @ValidateFiled(index=0,notNull=true,filedName="type"),
            @ValidateFiled(index=0,filedName="hot")
    })
	@Log
	@RequestMapping(value="/addProvice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addProvice(Provice provice,HttpServletRequest request, HttpServletResponse response) throws Exception{
		JsonResult result=new JsonResult();
		adminUserService.addProvice(provice);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteProvice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteProvice(Provice provice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		adminUserService.deleteProvice(provice);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="code"),
            @ValidateFiled(index=0,filedName="type"),
            @ValidateFiled(index=0,filedName="hot")
    })
	@Log
	@RequestMapping(value="/updateProvice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateProvice(Provice provice,HttpServletRequest request, HttpServletResponse respons) throws Exception{
		JsonResult result=new JsonResult();
		adminUserService.updateProvice(provice);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getProvice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getProvice(Provice provice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Provice proviceInDB=adminUserService.getProvice(provice);
		result.setData(proviceInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getProviceList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getProviceList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		param.put("orderBy", "hot");//按省份热度排序
		List<Provice>list=adminUserService.getProviceList(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	//*************************************城市管理  结束*****************************************
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="code"),
            @ValidateFiled(index=0,notNull=true,filedName="provinceId")
    })
	@Log
	@RequestMapping(value="/addCity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addCity(City city,HttpServletRequest request, HttpServletResponse response) throws Exception{
		JsonResult result=new JsonResult();
		adminUserService.addCity(city);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteCity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteCity(City city,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		adminUserService.deleteCity(city);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="code"),
            @ValidateFiled(index=0,filedName="provinceId")
    })
	@Log
	@RequestMapping(value="/updateCity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateCity(City city,HttpServletRequest request, HttpServletResponse response) throws Exception{
		JsonResult result=new JsonResult();
		adminUserService.updateCity(city);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getCity.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCity(City city,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		City cityInDB=adminUserService.getCity(city);
		result.setData(cityInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getCityList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getCityList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String provinceId=request.getParameter("provinceId");
		param.put("provinceId", provinceId);
		List<City>list=adminUserService.getCityList(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	//*************************************城市管理  结束*****************************************
	
	//*************************************小区管理  开始*****************************************
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="name"),
            @ValidateFiled(index=0,notNull=true,filedName="cityId"),
            @ValidateFiled(index=0,filedName="longitude"),
            @ValidateFiled(index=0,filedName="latitude"),
            @ValidateFiled(index=0,filedName="introduce"),
            @ValidateFiled(index=0,filedName="peripheral"),
            @ValidateFiled(index=0,filedName="keyWords"),
            @ValidateFiled(index=0,filedName="tagWords"),
            @ValidateFiled(index=0,filedName="seoTitle")
    })
	@Log
	@RequestMapping(value="/addDistrict.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addDistrict(District district,HttpServletRequest request, HttpServletResponse response) throws Exception{
		JsonResult result=new JsonResult();
		int c=adminUserService.addDistrict(district);
		if(c>0){
			Integer id=district.getId();
			
			FileServiceFactory factory=FileServiceFactory.getInstance();
			FileService fileService=factory.getFileService("local");
			
			MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
			MultipartFile img1=mulRequest.getFile("img1");
			if(img1!=null){
				//上传文件
				String oldFileName=img1.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(img1.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_COMMON);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile img2=mulRequest.getFile("img2");
			if(img2!=null){
				//上传文件
				String oldFileName=img2.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(img2.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_COMMON);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile img3=mulRequest.getFile("img3");
			if(img3!=null){
				//上传文件
				String oldFileName=img3.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(img3.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_COMMON);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile img4=mulRequest.getFile("img4");
			if(img4!=null){
				//上传文件
				String oldFileName=img4.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(img4.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_COMMON);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile img5=mulRequest.getFile("img5");
			if(img5!=null){
				//上传文件
				String oldFileName=img5.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(img5.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_COMMON);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile peripheralImg1=mulRequest.getFile("peripheralImg1");
			if(peripheralImg1!=null){
				//上传文件
				String oldFileName=peripheralImg1.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(peripheralImg1.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_PERIPHERAL);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile peripheralImg2=mulRequest.getFile("peripheralImg2");
			if(peripheralImg2!=null){
				//上传文件
				String oldFileName=peripheralImg2.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(peripheralImg2.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_PERIPHERAL);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile peripheralImg3=mulRequest.getFile("peripheralImg3");
			if(peripheralImg3!=null){
				//上传文件
				String oldFileName=peripheralImg3.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(peripheralImg3.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_PERIPHERAL);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile peripheralImg4=mulRequest.getFile("peripheralImg4");
			if(peripheralImg4!=null){
				//上传文件
				String oldFileName=peripheralImg4.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(peripheralImg4.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_PERIPHERAL);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
			
			MultipartFile peripheralImg5=mulRequest.getFile("peripheralImg5");
			if(peripheralImg5!=null){
				//上传文件
				String oldFileName=peripheralImg5.getOriginalFilename();
				String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
				//重新生成文件名
				String uuid=UUID.randomUUID().toString().replace("-", "");
				String fileName=uuid+"."+suffix;
				//需要带上路径
				fileName="district"+id+"/"+fileName;
				String url=fileService.uploadFile(peripheralImg5.getBytes(), fileName, "123456789");
				
				DistrictImg img=new DistrictImg();
				img.setDistrictId(id);
				img.setType(DistrictImg.TYPE_PERIPHERAL);
				img.setUrl(url);
				adminUserService.addDistrictImg(img);
			}
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteDistrict.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteDistrict(District district,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		adminUserService.deleteDistrict(district);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="name"),
            @ValidateFiled(index=0,filedName="cityId"),
            @ValidateFiled(index=0,filedName="longitude"),
            @ValidateFiled(index=0,filedName="latitude"),
            @ValidateFiled(index=0,filedName="introduce"),
            @ValidateFiled(index=0,filedName="peripheral"),
            @ValidateFiled(index=0,filedName="keyWords"),
            @ValidateFiled(index=0,filedName="tagWords"),
            @ValidateFiled(index=0,filedName="seoTitle")
    })
	@Log
	@RequestMapping(value="/updateDistrict.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateDistrict(District district,HttpServletRequest request, HttpServletResponse response) throws Exception{
		JsonResult result=new JsonResult();
		adminUserService.updateDistrict(district);
		
		Integer id=district.getId();
		
		FileServiceFactory factory=FileServiceFactory.getInstance();
		FileService fileService=factory.getFileService("local");
		
		MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
		MultipartFile img1=mulRequest.getFile("img1");
		if(img1!=null){
			//上传文件
			String oldFileName=img1.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(img1.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_COMMON);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile img2=mulRequest.getFile("img2");
		if(img2!=null){
			//上传文件
			String oldFileName=img2.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(img2.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_COMMON);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile img3=mulRequest.getFile("img3");
		if(img3!=null){
			//上传文件
			String oldFileName=img3.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(img3.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_COMMON);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile img4=mulRequest.getFile("img4");
		if(img4!=null){
			//上传文件
			String oldFileName=img4.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(img4.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_COMMON);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile img5=mulRequest.getFile("img5");
		if(img5!=null){
			//上传文件
			String oldFileName=img5.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(img5.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_COMMON);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile peripheralImg1=mulRequest.getFile("peripheralImg1");
		if(peripheralImg1!=null){
			//上传文件
			String oldFileName=peripheralImg1.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(peripheralImg1.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_PERIPHERAL);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile peripheralImg2=mulRequest.getFile("peripheralImg2");
		if(peripheralImg2!=null){
			//上传文件
			String oldFileName=peripheralImg2.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(peripheralImg2.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_PERIPHERAL);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile peripheralImg3=mulRequest.getFile("peripheralImg3");
		if(peripheralImg3!=null){
			//上传文件
			String oldFileName=peripheralImg3.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(peripheralImg3.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_PERIPHERAL);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile peripheralImg4=mulRequest.getFile("peripheralImg4");
		if(peripheralImg4!=null){
			//上传文件
			String oldFileName=peripheralImg4.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(peripheralImg4.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_PERIPHERAL);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		MultipartFile peripheralImg5=mulRequest.getFile("peripheralImg5");
		if(peripheralImg5!=null){
			//上传文件
			String oldFileName=peripheralImg5.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(peripheralImg5.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(DistrictImg.TYPE_PERIPHERAL);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getDistrict.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getDistrict(District district,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		District districtInDB=adminUserService.getDistrict(district);
		result.setData(districtInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getDistrictList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getDistrictList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String cityIdStr=request.getParameter("cityId");
		if(StringUtils.isNotEmpty(cityIdStr)){
			Integer cityId=Integer.parseInt(cityIdStr);
			param.put("cityId", cityId);
		}
		List<District>list=adminUserService.getDistrictList(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getDistrictHouseList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getDistrictHouseList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		String cityIdStr=request.getParameter("cityId");
		if(StringUtils.isNotEmpty(cityIdStr)){
			Integer cityId=Integer.parseInt(cityIdStr);
			param.put("cityId", cityId);
		}
		String districtIdStr=request.getParameter("districtId");
		if(StringUtils.isNotEmpty(districtIdStr)){
			Integer districtId=Integer.parseInt(districtIdStr);
			param.put("districtId", districtId);
		}
		List<Map<String,Object>>list=adminUserService.getDistrictHouseList(param);
		int total=adminUserService.getDistrictHouseListCount(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		ret.put("total", total);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteDistrictImg.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteDistrictImg(DistrictImg districtImg,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		adminUserService.deleteDistrictImg(districtImg);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="districtId"),
            @ValidateFiled(index=0,notNull=true,filedName="type")
    })
	@Log
	@RequestMapping(value="/uploadDistrictImg.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult uploadDistrictImg(DistrictImg districtImg,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Integer id=Integer.parseInt(request.getParameter("districtId"));
		String type=request.getParameter("type");
		MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
		MultipartFile img5=mulRequest.getFile("img");
		if(img5!=null){
			FileServiceFactory factory=FileServiceFactory.getInstance();
			FileService fileService=factory.getFileService("local");
			
			//上传文件
			String oldFileName=img5.getOriginalFilename();
			String suffix=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			//重新生成文件名
			String uuid=UUID.randomUUID().toString().replace("-", "");
			String fileName=uuid+"."+suffix;
			//需要带上路径
			fileName="district"+id+"/"+fileName;
			String url=fileService.uploadFile(img5.getBytes(), fileName, "123456789");
			
			DistrictImg img=new DistrictImg();
			img.setDistrictId(id);
			img.setType(type);
			img.setUrl(url);
			adminUserService.addDistrictImg(img);
		}
		
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	//*************************************小区管理  结束*****************************************
	
	//*************************************房价管理  开始*****************************************
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="houseId"),
            @ValidateFiled(index=0,notNull=true,filedName="day"),
            @ValidateFiled(index=0,notNull=true,filedName="priceType"),
            @ValidateFiled(index=0,filedName="collectionAmount"),
            @ValidateFiled(index=0,filedName="orderAmount"),
            @ValidateFiled(index=0,filedName="commissionRate")
    })
	@Log
	@RequestMapping(value="/addHouseDayPrice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult addHouseDayPrice(HouseDayPrice houseDayPrice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		houseService.addHouseDayPrice(houseDayPrice);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@Log
	@RequestMapping(value="/deleteHouseDayPrice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult deleteHouseDayPrice(HouseDayPrice houseDayPrice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		houseService.deleteHouseDayPrice(houseDayPrice);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
			@ValidateFiled(index=0,notNull=true,filedName="id"),
            @ValidateFiled(index=0,filedName="priceType"),
            @ValidateFiled(index=0,filedName="collectionAmount"),
            @ValidateFiled(index=0,filedName="orderAmount"),
            @ValidateFiled(index=0,filedName="commissionRate")
    })
	@Log
	@RequestMapping(value="/updateHouseDayPrice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult updateHouseDayPrice(HouseDayPrice houseDayPrice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		houseService.updateHouseDayPrice(houseDayPrice);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="id")
    })
	@RequestMapping(value="/getHouseDayPrice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseDayPrice(HouseDayPrice houseDayPrice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		HouseDayPrice houseDayPriceInDB=houseService.getHouseDayPrice(houseDayPrice);
		result.setData(houseDayPriceInDB);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getHouseDayPriceList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseDayPriceList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>para=new HashMap<String,Object>();
		para.put("startTime", param.getStartTime());
		para.put("endTime", param.getEndTime());
		String houseId=request.getParameter("houseId");
		para.put("houseId", Integer.parseInt(houseId));
		
		List<HouseDayPrice>list=houseService.getHouseDayPriceList(para);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@ValidateGroup(fileds = {//校验字段信息
            @ValidateFiled(index=0,notNull=true,filedName="houseId"),
            @ValidateFiled(index=0,notNull=true,filedName="day"),
            @ValidateFiled(index=0,filedName="orderAmount"),
            @ValidateFiled(index=0,filedName="collectionAmount")
    })
	@Log
	@RequestMapping(value="/editHouseDayPrice.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult editHouseDayPrice(HouseDayPrice houseDayPrice,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		int c=houseService.editHouseDayPrice(houseDayPrice);
		if(c>0){
			result.setCode(JsonResult.CODE_SUCCESS);
		}else{
			result.setCode(JsonResult.CODE_FAIL);
		}
		return result;
	}
	@Log
	@RequestMapping(value="/editHouseDayPriceList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult editHouseDayPriceList(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		String[]houseDayPriceItems=request.getParameterValues("houseDayPrice[]");
		if(houseDayPriceItems==null||houseDayPriceItems.length==0){
			result.setCode(JsonResult.CODE_SUCCESS);
			return result;
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<HouseDayPrice>houseDayPriceList=new ArrayList<HouseDayPrice>();
		//转换成对象
		for(String s:houseDayPriceItems){
			//houseId=15&day=2018-02-02&orderAmount=11&collectionAmount=111
			if(StringUtils.isEmpty(s)){
				continue;
			}
			String[]items=s.split("&");
			if(items==null||items.length==0){
				continue;
			}
			Map<String,String>map=new HashMap<String,String>();
			for(String ss:items){
				String[]kv=ss.split("=");
				if(kv.length<2){
					continue;
				}
				map.put(kv[0], kv[1]);
			}
			if(!map.isEmpty()){
				HouseDayPrice houseDayPrice=new HouseDayPrice();
				houseDayPrice.setHouseId(map.get("houseId")==null?null:Integer.parseInt(map.get("houseId")));
				String _day=map.get("day");
				if(StringUtils.isNotEmpty(_day)){
					Date day=df.parse(_day);
					houseDayPrice.setDay(day);
				}
				houseDayPrice.setOrderAmount(map.get("orderAmount")==null?null:
					Double.parseDouble(map.get("orderAmount")));
				houseDayPrice.setCollectionAmount(map.get("collectionAmount")==null?null:
					Double.parseDouble(map.get("collectionAmount")));
				
				houseDayPriceList.add(houseDayPrice);
			}
		}
		
		houseService.editHouseDayPriceList(houseDayPriceList);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getHouseListOfBeOverdue.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseListOfBeOverdue(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		List<Map<String,Object>>list=houseService.getHouseListOfBeOverdue(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		if(list!=null){
			ret.put("total", list.size());
		}else{
			ret.put("total", 0);
		}
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	@RequestMapping(value="/getHouseListOfWillBeOverdue.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseListOfWillBeOverdue(HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>param=new HashMap<String,Object>();
		List<Map<String,Object>>list=houseService.getHouseListOfWillBeOverdue(param);
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		if(list!=null){
			ret.put("total", list.size());
		}else{
			ret.put("total", 0);
		}
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	
	//*************************************房价管理  结束*****************************************
	
	@RequestMapping(value="/getHouseAttributeList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseAttributeList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>para=new HashMap<String,Object>();
		List<HouseAttribute>list=houseService.getHouseAttributeList(para);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getHouseFacilityList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseFacilityList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>para=new HashMap<String,Object>();
		List<HouseFacility>list=houseService.getHouseFacilityList(para);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
	@RequestMapping(value="/getHouseReceptionList.json", method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public JsonResult getHouseReceptionList(CommonParam param,HttpServletRequest request, HttpServletResponse response,JsonResult result) throws Exception{
		Map<String,Object>para=new HashMap<String,Object>();
		List<HouseReception>list=houseService.getHouseReceptionList(para);
		
		Map<String,Object>ret=new HashMap<String,Object>();
		ret.put("list", list);
		result.setData(ret);
		result.setCode(JsonResult.CODE_SUCCESS);
		return result;
	}
	
}
