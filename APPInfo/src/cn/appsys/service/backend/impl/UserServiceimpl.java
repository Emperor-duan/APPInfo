package cn.appsys.service.backend.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.appsys.dao.backend.user.UserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.UserService;

@Service("userService")
public class UserServiceimpl implements UserService {

	@Resource
	private UserMapper userapper;

	@Override
	public BackendUser getuser(String userCode, String userPassword) {
		return userapper.getuser(userCode, userPassword);
	}

}
