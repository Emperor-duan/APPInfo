package cn.appsys.service.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	public DevUser getDevUser(String devCode,String devPassword);
}
