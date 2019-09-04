package cn.appsys.service.backend;

import cn.appsys.pojo.BackendUser;

public interface UserService {
    
	public BackendUser getuser(String userCode,String userPassword);

	
}
