package cn.appsys.service.appversion;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppversionService {
	public List<AppVersion> getAppversionList(int id);
	
	public boolean insAppversion(AppVersion appVersion);
	
	public AppVersion getAppversion(int id);
	
	public boolean delapk(int id);
	
	public boolean updAppVersion(AppVersion appVersion);
	
	public AppVersion getAppVersionxing();
	
	public boolean delAppVersion(int appId);
}
