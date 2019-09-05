package cn.appsys.service.backend;

import java.util.List;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

public interface AuditService {

	public List<AppInfo> appinfoList(int currentPageNo, int pageSize,String softwareName,String flatformName,String categoryLevel1Name,String categoryLevel2Name,String categoryLevel3Name);

	public int appinfoCount();
	
	public List<DataDictionary> pingtaiList();
	
	public List<AppCategory> yijicaidanList();
	
	public List<AppCategory> categoryLevel2List(int mue);
	
	public List<AppCategory> categoryLevel3List(int mue);
	
	public AppInfo appinfoList1(int id);
	
	public AppVersion appinfoList2 (int id);
	
	public int xiugai(int status,int id);
}
