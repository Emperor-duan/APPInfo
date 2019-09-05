package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	public List<AppInfo> getAppInfoList(@Param("softwareName")String softwareName,
			@Param("categoryLevel1")int categoryLevel1,
			@Param("categoryLevel2")int categoryLevel2,
			@Param("categoryLevel3")int categoryLevel3,
			@Param("status")int status,
			@Param("flatformId")int flatformId,
			@Param("currentPageNo")int currentPageNo,
			@Param("pageSize")int pageSize);
	public int count(@Param("softwareName")String softwareName,
			@Param("categoryLevel1")int categoryLevel1,
			@Param("categoryLevel2")int categoryLevel2,
			@Param("categoryLevel3")int categoryLevel3,
			@Param("status")int status,
			@Param("flatformId")int flatformId);
	public int insAppInfo(AppInfo appInfo);
	
	public AppInfo APKNameexsit(@Param("APKName")String APKName);
	
	public AppInfo getAppInfo(@Param("id")int id);
	
	public int updAppInfo(AppInfo appInfo);
	
	public int dellogo(@Param("id")int id);
	
	public int updAversion(@Param("versionId")int versionId,@Param("id")int id);
	
	public int delAppInfo(int id);
}
