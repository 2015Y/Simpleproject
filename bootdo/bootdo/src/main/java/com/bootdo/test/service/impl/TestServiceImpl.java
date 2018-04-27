package com.bootdo.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.test.dao.TestMapper;
import com.bootdo.test.domain.TestDO;
import com.bootdo.test.service.TestService;



@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private TestMapper testMapper;
	
	@Override
	public TestDO get(Integer testId){
		return testMapper.get(testId);
	}
	
	@Override
	public List<TestDO> list(Map<String, Object> map){
		return testMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return testMapper.count(map);
	}
	
	@Override
	public int save(TestDO test){
		return testMapper.save(test);
	}
	
	@Override
	public int update(TestDO test){
		return testMapper.update(test);
	}
	
	@Override
	public int remove(Integer testId){
		return testMapper.remove(testId);
	}
	
	@Override
	public int batchRemove(Integer[] testIds){
		return testMapper.batchRemove(testIds);
	}
	
}
