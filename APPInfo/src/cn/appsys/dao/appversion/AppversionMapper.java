package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppversionMapper {
	public List<AppVersion> getAppVersionList(@Param("id")int id);
	
	public int insAppVersion(AppVersion appVersion);
	
	public AppVersion getAppVersion(@Param("id")int id);
	
	public int delapk(@Param("id")int id);
	
	public int updAppVersion(AppVersion appVersion);
	
	public AppVersion getAppVersionxing();
	
	public int delAppVersion(@Param("appId")int appId);
}
