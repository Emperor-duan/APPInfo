package cn.appsys.dao.backend.audit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

public interface AuditMapper {

	/**
	 * 获取全部信息
	 * 
	 * @return
	 */
	public List<AppInfo> appinfoList(@Param(value = "from") Integer currentPageNo,
			                         @Param(value = "pageSize") Integer pageSize,
			                         @Param(value = "softwareName")String softwareName,
			                         @Param(value = "flatformName")String flatformName,
			                         @Param(value = "categoryLevel1Name")String categoryLevel1Name,
			                         @Param(value = "categoryLevel2Name")String categoryLevel2Name,
			                         @Param(value = "categoryLevel3Name")String categoryLevel3Name);
	/**
	 * 获取总行数
	 */
	public int appinfoCount();

	/**
	 * 查询所属平台
	 */
	public List<DataDictionary> pingtaiList();
	
	/**
	 * 查询一级菜单
	 */
	public List<AppCategory> yijicaidanList();
	
	/**
	 * 查询二级菜单
	 * @return
	 */
	public List<AppCategory> categoryLevel2List(@Param(value="categoryLevel1")Integer mue);
	
	/**
	 * 查询三级菜单
	 * @return
	 */
	public List<AppCategory> categoryLevel3List(@Param(value="categoryLevel2")Integer mue);
    
	
	/**
	 * 根据id查找
	 */
	public AppInfo appinfoList1(@Param(value="id")Integer id);
	
	public AppVersion appinfoList2(@Param(value="id")Integer id);
	
	/**
	 * 修改状态
	 */
	public int xiugai(@Param(value="status")Integer status,@Param(value="id")Integer id);
}
