package cn.appsys.service.appinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Autowired
	private AppInfoMapper appInfoMapper;
	
	@Override
	public List<AppInfo> getAppInfoList(String softwareName, int categoryLevel1, int categoryLevel2, int categoryLevel3,
			int status, int flatformId, int currentPageNo, int pageSize) {
		return appInfoMapper.getAppInfoList(softwareName, categoryLevel1, categoryLevel2, categoryLevel3, status, flatformId, currentPageNo, pageSize);
	}

	@Override
	public int count(String softwareName, int categoryLevel1, int categoryLevel2, int categoryLevel3, int status,
			int flatformId) {
		return appInfoMapper.count(softwareName, categoryLevel1, categoryLevel2, categoryLevel3, status, flatformId);
	}

}
