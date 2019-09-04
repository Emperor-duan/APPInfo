package cn.appsys.service.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	public List<AppInfo> getAppInfoList(String softwareName,int categoryLevel1,int categoryLevel2,int categoryLevel3,int status,int flatformId,int currentPageNo,int pageSize);
	
	public int count(String softwareName,int categoryLevel1,int categoryLevel2,int categoryLevel3,int status,int flatformId);
	
	public boolean insAppInfo(AppInfo appInfo);
	
	public boolean APKNameexsit(String APKName);
	
	public AppInfo getAppInfo(int id);
	
	public boolean updAppInfo(AppInfo appInfo);
	
	public boolean dellogo(int id);
}
