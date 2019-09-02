package cn.appsys.controller.developer;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppInfoService;
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
		List<AppInfo> appInfolist = appInfoService.getAppInfoList(querySoftwareName, _queryCategoryLevel1, _queryCategoryLevel2, _queryCategoryLevel3, _queryStatus, _queryFlatformId, currentPageNo, page.getPageSize());
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
		System.err.println("ddddddddddddddddddd");
		if (pid.equals("1")&&pid.equals("2")) {
			List<AppCategory> appCategorielist = appCategoryService.level2(Integer.parseInt(pid));
			return JSON.toJSONString(appCategorielist);
		}else {
			List<AppCategory> appCategorielist = appCategoryService.level3(Integer.parseInt(pid));
			return JSON.toJSONString(appCategorielist);
		}
	}
}
