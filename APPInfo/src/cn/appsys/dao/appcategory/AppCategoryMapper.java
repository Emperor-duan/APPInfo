package cn.appsys.dao.appcategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	public List<AppCategory> level1();
	
	public List<AppCategory> level2(int level1);
	
	public List<AppCategory> level3(int level2);
}
