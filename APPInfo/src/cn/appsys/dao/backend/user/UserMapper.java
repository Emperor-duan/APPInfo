package cn.appsys.dao.backend.user;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface UserMapper {
	/**
	 * 查询所有
	 */
	public BackendUser getuser(@Param("userCode")String userCode,@Param("userPassword")String userPassword);
	
}
