package cn.appsys.dao.backend.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AuditMapper {

	/**
	 * 获取全部信息
	 * 
	 * @return
	 */
	public List<AppInfo> appinfoList(@Param(value = "from") Integer currentPageNo,
			   @Param(value = "pageSize") Integer pageSize);

	/**
	 * 获取总行数
	 */
	public int appinfoCount();

	/**
	 * 动态查询下拉框列表数据
	 */
     public List<AppInfo> categoryLevelList();
}
