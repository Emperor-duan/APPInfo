package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {
	public DevUser getDevUser(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
}
