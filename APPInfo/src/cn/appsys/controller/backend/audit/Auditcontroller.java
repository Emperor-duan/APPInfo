package cn.appsys.controller.backend.audit;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.AppInfo;
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
	public String applist(Model model, @RequestParam(value = "pageIndex", required = false) String pageIndex) {
		List<AppInfo> list = null;
		logger.debug("进入商品审核页面------------------------------------------------");

		// 设置页面容量
		int pageSize = Constants.pageSize;
		// 当前页吗
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

		list = auditService.appinfoList(currentPageNo, pageSize);

		model.addAttribute("pages", page);
		model.addAttribute("appInfoList", list);
		return "backend/applist";

	}
}
