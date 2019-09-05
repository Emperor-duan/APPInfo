package cn.appsys.controller.developer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.service.appversion.AppversionService;
import cn.appsys.service.datadictionary.DataDictionaryService;
import cn.appsys.tools.Page;

@Controller
@RequestMapping("/dev")
public class DevController {
	
	@Autowired
	private AppCategoryService appCategoryService;
	
	@Autowired
	private AppInfoService appInfoService;
	
	@Autowired
	private DataDictionaryService dataDictionaryService;
	
	@Autowired
	private AppversionService appversionService;
	
	@RequestMapping("/logout")
	public String logout(){
		return "devlogin";
	}
	
	@RequestMapping("/flatform/app/list")
	public String appinfolist(Model model,
			@RequestParam(value="querySoftwareName",required=false)String querySoftwareName,
			@RequestParam(value="queryStatus",required=false)String queryStatus,
			@RequestParam(value="queryFlatformId",required=false)String queryFlatformId,
			@RequestParam(value="queryCategoryLevel1",required=false)String queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false)String queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false)String queryCategoryLevel3,
			@RequestParam(value="pageIndex",required=false)String pageIndex,HttpSession session){
		int currentPageNo = 1;
		int _queryFlatformId = 0;
		int _queryStatus = 0;
		int _queryCategoryLevel1 = 0;
		int _queryCategoryLevel2 = 0;
		int _queryCategoryLevel3 = 0;
		if (queryStatus != null && !queryStatus.equals("")) {
			_queryStatus = Integer.parseInt(queryStatus);
		}
		if (queryFlatformId != null && !queryFlatformId.equals("")) {
			_queryFlatformId = Integer.parseInt(queryFlatformId);
		}
		if (queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")) {
			_queryCategoryLevel1 = Integer.parseInt(queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")) {
			_queryCategoryLevel2 = Integer.parseInt(queryCategoryLevel2);
		}
		if (queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")) {
			_queryCategoryLevel3 = Integer.parseInt(queryCategoryLevel3);
		}
		if (pageIndex != null && pageIndex != "") {
			currentPageNo = Integer.parseInt(pageIndex);
		}
		Page page = new Page();
		page.setCurrentPageNo(currentPageNo);
		page.setTotalCount(appInfoService.count(querySoftwareName, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryStatus, _queryFlatformId));
		int totalpageCount = page.getTotalPageCount();
		if (currentPageNo<1) {
			currentPageNo = 1;
		}else if (currentPageNo>totalpageCount) {
			currentPageNo = totalpageCount;
		}
		List<AppInfo> appInfolist = appInfoService.getAppInfoList(querySoftwareName, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryStatus, _queryFlatformId, (currentPageNo - 1) * page.getPageSize(), page.getPageSize());
		List<AppCategory> appCategorielist = appCategoryService.level1();
		List<DataDictionary> statuslist = dataDictionaryService.app();
		List<DataDictionary> flatFormlist = dataDictionaryService.pingtai();
		model.addAttribute("querySoftwareName",querySoftwareName);
		model.addAttribute("pages",page);
		model.addAttribute("queryStatus",queryStatus);
		model.addAttribute("queryFlatformId",queryFlatformId);
		model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
		model.addAttribute("appInfoList",appInfolist);
		model.addAttribute("categoryLevel1List",appCategorielist);
		model.addAttribute("statusList",statuslist);
		model.addAttribute("flatFormList",flatFormlist);
		return "/developer/appinfolist";
	}
	
	@RequestMapping(value="/flatform/app/categorylevellist",method=RequestMethod.GET)
	@ResponseBody
	public Object categorylevellist(@RequestParam(value="pid")String pid){
		if (pid.equals("")) {
			List<AppCategory> appCategorielist = appCategoryService.level1();
			return JSON.toJSONString(appCategorielist);
		}else if (pid.equals("1")&&pid.equals("2")) {
			List<AppCategory> appCategorielist = appCategoryService.level2(Integer.parseInt(pid));
			return JSON.toJSONString(appCategorielist);
		}else {
			List<AppCategory> appCategorielist = appCategoryService.level3(Integer.parseInt(pid));
			return JSON.toJSONString(appCategorielist);
		}
	}
	
	@RequestMapping(value="/flatform/app/appinfoadd")
	public String addHtml(){
		return "/developer/appinfoadd";
	}
	
	@RequestMapping(value="/flatform/app/datadictionarylist")
	@ResponseBody
	public Object datadictionarylist(@RequestParam(value="tcode")String tcode){
		List<DataDictionary> dataDictionarielist = dataDictionaryService.pingtai();
		return JSON.toJSONString(dataDictionarielist);
	}
	
	@RequestMapping(value="/flatform/app/appinfoaddsave")
	public String appinfoaddsave(AppInfo appInfo,HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required=false) MultipartFile attach){
		String logoPicPath="";
		String logoLocPath="";
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000;
			if (attach.getSize()>filesize) {
				request.setAttribute("fileUploadError", "上传大小不能超过500KB");
				return "/developer/appinfoadd";
			}else if (prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||prefix.equalsIgnoreCase("jpeg")||prefix.equalsIgnoreCase("pneg")) {
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "文件上传失败！");
					return "/developer/appinfoadd";
				}
				logoLocPath = path + File.separator + fileName;
				String s = logoLocPath.replace("\\",",/");
				String[] array = s.split(",");
				for (int i = 3; i < array.length; i++) {
					logoPicPath +=array[i];
				}
			}else {
				request.setAttribute("fileUploadError", "文件格式不正确！");
				return "/developer/appinfoadd";
			}
		}
		appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		if (appInfoService.insAppInfo(appInfo)) {
			return "redirect:/dev/flatform/app/list";
		}
		return "/developer/appinfoadd";
	}
	
	@RequestMapping(value="/flatform/app/apkexist",method=RequestMethod.GET)
	@ResponseBody
	public Object apkexist(@RequestParam(value="APKName")String apkName){
		HashMap<String , Object> APKNameMap = new  HashMap<String, Object>();
		if (apkName!=null) {
			if (appInfoService.APKNameexsit(apkName)) {
				APKNameMap.put("APKName", "noexist");
			}else {
				APKNameMap.put("APKName", "exist");
			}
		}else{
			APKNameMap.put("APKName", "empty");
		}
		return JSON.toJSONString(APKNameMap);
	}
	
	
	@RequestMapping(value="/flatform/app/appinfomodify")
	public String appinfomodify(String id,Model model){
		AppInfo appInfo = appInfoService.getAppInfo(Integer.parseInt(id));
		model.addAttribute("appInfo",appInfo);
		return "/developer/appinfomodify";
	}
	
	@RequestMapping(value="/flatform/app/delfile",method=RequestMethod.GET)
	@ResponseBody
	public Object delfile(@RequestParam(value="id")String id,@RequestParam(value="flag")String flag){
		HashMap<String, Object> delfilemap = new HashMap<String,Object>();
		if (flag.equals("json")) {
			if (appInfoService.dellogo(Integer.parseInt(id))) {
				delfilemap.put("result", "success");
			}else {
				delfilemap.put("result", "failed");
			}
			
		}else if (flag.equals("apk")) {
			if (appversionService.delapk(Integer.parseInt(id))) {
				delfilemap.put("result", "success");
			}else {
				delfilemap.put("result", "failed");
			}
		}else{
			delfilemap.put("result", "failed");
		}
		return JSON.toJSONString(delfilemap);
	}
	
	@RequestMapping(value="/flatform/app/appinfomodifysave")
	public String appinfomodifysave(AppInfo appInfo,HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="attach",required=false) MultipartFile attach){
		String logoPicPath="";
		String logoLocPath="";
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000;
			if (attach.getSize()>filesize) {
				session.setAttribute("fileUploadError", "上传大小不能超过500KB");
				return "redirect:/flatform/app/appinfomodify?id="+appInfo.getId();
			}else if (prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||prefix.equalsIgnoreCase("jpeg")||prefix.equalsIgnoreCase("pneg")) {
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("fileUploadError", "文件上传失败！");
					return "redirect:/flatform/app/appinfomodify?id="+appInfo.getId();
				}
				logoLocPath = path + File.separator + fileName;
				String s = logoLocPath.replace("\\",",/");
				String[] array = s.split(",");
				for (int i = 3; i < array.length; i++) {
					logoPicPath +=array[i];
				}
			}else {
				session.setAttribute("fileUploadError", "文件格式不正确！");
				return "redirect:/flatform/app/appinfomodify?id="+appInfo.getId();
			}
		}
		appInfo.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setLogoLocPath(logoLocPath);
		if (appInfoService.updAppInfo(appInfo)) {
			return "redirect:/dev/flatform/app/list";
		}
		return "redirect:/flatform/app/appinfomodify?id="+appInfo.getId();
	}
	
	@RequestMapping(value="/flatform/app/appversionadd")
	public String appversionadd(@RequestParam(value="id")String id,Model model){
		List<AppVersion> appVersionlist = appversionService.getAppversionList(Integer.parseInt(id));
		AppVersion appVersion = new AppVersion();
		appVersion.setAppId(Integer.parseInt(id));
		model.addAttribute("appVersion",appVersion);
		model.addAttribute("appVersionList",appVersionlist);
		return "/developer/appversionadd";
	}
	
	@RequestMapping(value="/flatform/app/addversionsave")
	public String addversionsave(AppVersion appVersion,HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required = false) MultipartFile attach){
		String downloadLink = null;
		String apkLocPath = null;
		String apkFileName = null;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000000;
			if (attach.getSize()>filesize) {
				request.setAttribute("fileUploadError", "上传大小不能超过500MB");
				return "/developer/appversionadd";
			}else if (prefix.equalsIgnoreCase("apk")) {
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + appVersion.getVersionNo() + ".apk";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "文件上传失败！");
					return "/developer/appversionadd";
				}
				apkLocPath = path + File.separator + fileName;
				String s = apkLocPath.replace("\\",",/");
				String[] array = s.split(",");
				downloadLink = "";
				for (int i = 3; i < array.length; i++) {
					downloadLink +=array[i];
				}
				String[] array1 = downloadLink.split("/");
				apkFileName = array1[array1.length-1];
				System.err.println(apkFileName+","+apkLocPath+","+downloadLink);
			}else {
				request.setAttribute("fileUploadError", "文件格式不正确！");
				return "/developer/appversionadd";
			}
		}
		appVersion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appVersion.setCreationDate(new Date());
		appVersion.setApkFileName(apkFileName);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setDownloadLink(downloadLink);
		if (appversionService.insAppversion(appVersion)) {
			AppVersion appVersion2 = appversionService.getAppVersionxing();
			Integer versionId = appVersion2.getId();
			Integer id = appVersion2.getAppId();
			if (appInfoService.updAversion(versionId,id)) {
				return "redirect:/dev/flatform/app/list";
			}
		}
		return "/developer/appversionadd";
	}
	
	@RequestMapping(value="/flatform/app/appversionmodify")
	public String appversionmodify(@RequestParam(value="vid")String vid,@RequestParam(value="aid")String aid,Model model){
		List<AppVersion> appVersionlist = appversionService.getAppversionList(Integer.parseInt(aid));
		AppVersion appVersion = appversionService.getAppversion(Integer.parseInt(vid));
		model.addAttribute("appVersion",appVersion);
		model.addAttribute("appVersionList",appVersionlist);
		return "/developer/appversionmodify";
	}
	
	@RequestMapping(value="/flatform/app/appversionmodifysave",method=RequestMethod.POST)
	public String appversionmodifysave(AppVersion appVersion,HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="attach",required = false) MultipartFile attach){
		String downloadLink = null;
		String apkLocPath = null;
		String apkFileName = null;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000000;
			if (attach.getSize()>filesize) {
				request.setAttribute("fileUploadError", "上传大小不能超过500MB");
				return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()+"&aid="+appVersion.getAppId();
			}else if (prefix.equalsIgnoreCase("apk")) {
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + appVersion.getVersionNo() + ".apk";
				File targetFile = new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "文件上传失败！");
					return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()+"&aid="+appVersion.getAppId();
				}
				apkLocPath = path + File.separator + fileName;
				String s = apkLocPath.replace("\\",",/");
				String[] array = s.split(",");
				downloadLink = "";
				for (int i = 3; i < array.length; i++) {
					downloadLink +=array[i];
				}
				String[] array1 = downloadLink.split("/");
				apkFileName = array1[array1.length-1];
			}else {
				request.setAttribute("fileUploadError", "文件格式不正确！");
				return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()+"&aid="+appVersion.getAppId();
			}
		}
		appVersion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appVersion.setCreationDate(new Date());
		appVersion.setApkFileName(apkFileName);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setDownloadLink(downloadLink);
		if (appversionService.updAppVersion(appVersion)) {
			return "redirect:/dev/flatform/app/list";
		}
		return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()+"&aid="+appVersion.getAppId();
	}
	
	@RequestMapping(value="flatform/app/appview/{id}")
	public String appview(@PathVariable String id,Model model){
		List<AppVersion> appVersionlist = appversionService.getAppversionList(Integer.parseInt(id));
		AppInfo appInfo = appInfoService.getAppInfo(Integer.parseInt(id));
		model.addAttribute("appInfo",appInfo);
		model.addAttribute("appVersionList",appVersionlist);
		return "/developer/appinfoview";
	}
	
	@RequestMapping(value="flatform/app/delapp",method=RequestMethod.GET)
	@ResponseBody
	public Object delapp(String id){
		HashMap<String, Object> app = new HashMap<String,Object>();
		if (appInfoService.getAppInfo(Integer.parseInt(id))!=null) {
			if (appversionService.delAppVersion(Integer.parseInt(id))) {
				if (appInfoService.delAppInfo(Integer.parseInt(id))) {
					app.put("delResult", "true");
				}else {
					app.put("delResult", "false");
				}
			}else {
				if (appInfoService.delAppInfo(Integer.parseInt(id))) {
					app.put("delResult", "true");
				}else {
					app.put("delResult", "false");
				}
			}
		}else {
			app.put("delResult", "notexist");
		}
		
		return JSON.toJSONString(app);
	}
	
	@RequestMapping(value="flatform/app/sale",method=RequestMethod.GET)
	@ResponseBody
	public Object sale(@RequestParam("id")String id){
		System.err.println("1111");
		HashMap<String, Object> appInfoMap = new HashMap<String,Object>();
		AppInfo appInfo = appInfoService.getAppInfo(Integer.parseInt(id));
		try {
			if (appInfo!=null) {
				appInfoMap.put("errorCode", "0");
				System.err.println(appInfo.getStatus());
				if (appInfo.getStatus() == 4) {
					appInfo.setStatus(5);
					if (appInfoService.updAppInfo(appInfo)) {
						appInfoMap.put("resultMsg", "success");
					}else{
						appInfoMap.put("resultMsg", "failed");
					}
				}else if (appInfo.getStatus() == 5) {
					appInfo.setStatus(4);
					if (appInfoService.updAppInfo(appInfo)) {
						appInfoMap.put("resultMsg", "success");
					}else{
						appInfoMap.put("resultMsg", "failed");
					}
				}
			}else {
				appInfoMap.put("errorCode", "param000001");
			}
		} catch (Exception e) {
			appInfoMap.put("errorCode", "exception000001");
		}
		return JSON.toJSONString(appInfoMap);
	}
}
