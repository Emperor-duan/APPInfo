package cn.appsys.controller.backend.audit;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AuditService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/audit")
public class Auditcontroller {

	Logger logger = Logger.getLogger(Auditcontroller.class);
	@Autowired
	private AuditService auditService;

	@RequestMapping(value = "/applist.html")
	public String applist(Model model, @RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "querySoftwareName", required = false) String softwareName,
			@RequestParam(value = "queryFlatformId", required = false) String flatformName,
			@RequestParam(value = "queryCategoryLevel1", required = false) String categoryLevel1Name,
			@RequestParam(value = "queryCategoryLevel2", required = false) String categoryLevel2Name,
			@RequestParam(value = "queryCategoryLevel3", required = false) String categoryLevel3Name) {
		List<AppInfo> list = null;
		// 设置页面容量
		int pageSize = Constants.pageSize;
		// 当前页吗
		if (categoryLevel1Name == null) {
			categoryLevel1Name = "";
		}
		if (categoryLevel2Name == null) {
			categoryLevel2Name = "";
		}
		if (categoryLevel3Name == null) {
			categoryLevel3Name = "";
		}
		int currentPageNo = 1;
		if (pageIndex != null) {
			currentPageNo = Integer.parseInt(pageIndex);
		}
		// 获取总行数
		int totalCount = auditService.appinfoCount();

		// 总页面数
		PageSupport page = new PageSupport();
		page.setCurrentPageNo(currentPageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		int totalPageCount = page.getTotalPageCount();
		// 控制首页和尾页
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}

		list = auditService.appinfoList(currentPageNo, pageSize, softwareName, flatformName, categoryLevel1Name,
				categoryLevel2Name, categoryLevel3Name);
		// 创建一个对象用于存储所属平台
		List<DataDictionary> appinfoList = auditService.pingtaiList();

		// 创建一个存储一级菜单的对象
		List<AppCategory> categoryLevel1List = auditService.yijicaidanList();

		model.addAttribute("categoryLevel1List", categoryLevel1List);

		model.addAttribute("flatFormList", appinfoList);
		model.addAttribute("pages", page);
		model.addAttribute("appInfoList", list);

		return "backend/applist";
	}

	@RequestMapping(value = "/categorylevellist.json")
	@ResponseBody
	public Object chuli(int pid) {
		List<AppCategory> categoryLevel2List = auditService.categoryLevel2List(pid);
		return JSON.toJSONString(categoryLevel2List);
	}

	@RequestMapping(value = "/categorylevel2ist.json")
	@ResponseBody
	public Object chuli2(int pid) {
		List<AppCategory> categoryLevel3List = auditService.categoryLevel3List(pid);
		return JSON.toJSONString(categoryLevel3List);
	}

	@RequestMapping(value = "/checksave")
	public String checkSave(Model model, int aid, int vid) {
		logger.debug("appInfo =========== > " + aid);
		AppInfo lsit = null;
		AppVersion appVersion = null;
		try {
			lsit = auditService.appinfoList1(aid);
			appVersion = auditService.appinfoList2(vid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("appInfo", lsit);
		model.addAttribute("appVersion", appVersion);

		return "backend/appcheck";
	}

	@RequestMapping(value = "checksave1")
	public String checksave(@RequestParam(value = "status", required = false) String status,@RequestParam(value = "id", required = false) String id) {
         logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+status);
         logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+id);
         int mue = 0;
         int statusint = 0;
         int idint = 0;
		if (status.equals("2")) {
			statusint =Integer.parseInt( status);
			idint = Integer.parseInt( id);
             mue = auditService.xiugai(statusint, idint);
		} else {
			statusint =Integer.parseInt( status);
			idint = Integer.parseInt( id);
           mue = auditService.xiugai(statusint, idint);
		}

		return "redirect:/audit/applist.html";
	}
}
