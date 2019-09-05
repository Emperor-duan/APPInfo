package cn.appsys.service.backend.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.appsys.dao.backend.audit.AuditMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AuditService;
@Service("auditService")
public class AuditServiceimpl implements AuditService {

	@Resource
	private AuditMapper auditMapper;

	public List<AppInfo> appinfoList(int currentPageNo, int pageSize,String softwareName,String flatformName,String categoryLevel1Name,String categoryLevel2Name,String categoryLevel3Name) {
		List<AppInfo> list = null;
		try {
			int mue = (currentPageNo - 1) * pageSize;
			list = auditMapper.appinfoList(mue, pageSize, softwareName, flatformName, categoryLevel1Name, categoryLevel2Name, categoryLevel3Name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int appinfoCount() {
		// TODO Auto-generated method stub
		return auditMapper.appinfoCount();
	}

	@Override
	public List<DataDictionary> pingtaiList() {
		// TODO Auto-generated method stub
		return auditMapper.pingtaiList();
	}

	@Override
	public List<AppCategory> yijicaidanList() {
		// TODO Auto-generated method stub
		return auditMapper.yijicaidanList();
	}

	@Override
	public List<AppCategory> categoryLevel2List(int mue) {
		// TODO Auto-generated method stub
		return auditMapper.categoryLevel2List(mue);
	}

	@Override
	public List<AppCategory> categoryLevel3List(int mue) {
		// TODO Auto-generated method stub
		return auditMapper.categoryLevel3List(mue);
	}

	@Override
	public AppInfo appinfoList1(int id) {
		// TODO Auto-generated method stub
		return auditMapper.appinfoList1(id);
	}

	@Override
	public AppVersion appinfoList2(int id) {
		// TODO Auto-generated method stub
		return auditMapper.appinfoList2(id);
	}

	@Override
	public int xiugai(int status, int id) {
		// TODO Auto-generated method stub
		return auditMapper.xiugai(status, id);
	}


}
