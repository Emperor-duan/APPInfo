package cn.appsys.service.backend;

import java.util.List;

import cn.appsys.pojo.AppInfo;

public interface AuditService {

	public List<AppInfo> appinfoList(int currentPageNo, int pageSize);

	public int appinfoCount();
}
