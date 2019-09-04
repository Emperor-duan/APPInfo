package cn.appsys.service.backend.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.appsys.dao.backend.audit.AuditMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.backend.AuditService;
@Service("auditService")
public class AuditServiceimpl implements AuditService {

	@Resource
	private AuditMapper auditMapper;

	public List<AppInfo> appinfoList(int currentPageNo, int pageSize) {
		List<AppInfo> list = null;
		try {
			int mue = (currentPageNo - 1) * pageSize;
			list = auditMapper.appinfoList(mue, pageSize);
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

}
