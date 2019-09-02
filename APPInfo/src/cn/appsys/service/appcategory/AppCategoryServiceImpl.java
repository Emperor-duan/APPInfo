package cn.appsys.service.appcategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService {
	
	@Autowired
	private AppCategoryMapper appCategoryMapper;

	@Override
	public List<AppCategory> level1() {
		return appCategoryMapper.level1();
	}

	@Override
	public List<AppCategory> level2(int level1) {
		return appCategoryMapper.level2(level1);
	}

	@Override
	public List<AppCategory> level3(int level2) {
		return appCategoryMapper.level3(level2);
	}

}
