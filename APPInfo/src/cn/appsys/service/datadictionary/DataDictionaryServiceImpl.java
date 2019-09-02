package cn.appsys.service.datadictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {
	
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	public List<DataDictionary> app() {
		return dataDictionaryMapper.app();
	}

	@Override
	public List<DataDictionary> pingtai() {
		return dataDictionaryMapper.pingtai();
	}

}
