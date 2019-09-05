package cn.appsys.service.appversion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppversionMapper;
import cn.appsys.pojo.AppVersion;

@Service("appversionService")
public class AppversionServiceImpl implements AppversionService {
	
	@Autowired
	AppversionMapper appversionMapper;

	@Override
	public List<AppVersion> getAppversionList(int id) {
		return appversionMapper.getAppVersionList(id);
	}

	@Override
	public boolean insAppversion(AppVersion appVersion) {
		boolean flag = false;
		if (appversionMapper.insAppVersion(appVersion)>0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public AppVersion getAppversion(int id) {
		return appversionMapper.getAppVersion(id);
	}

	@Override
	public boolean delapk(int id) {
		boolean flag = false;
		if (appversionMapper.delapk(id)>0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean updAppVersion(AppVersion appVersion) {
		boolean flag = false;
		if (appversionMapper.updAppVersion(appVersion)>0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public AppVersion getAppVersionxing() {
		return appversionMapper.getAppVersionxing();
	}

	@Override
	public boolean delAppVersion(int appId) {
		boolean flag = false;
		if (appversionMapper.delAppVersion(appId)>0) {
			flag = true;
		}
		return flag;
	}
}
