package com.bootdo.test.service;

import com.bootdo.test.domain.TestDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-26 10:54:46
 */
public interface TestService {
	
	TestDO get(Integer testId);
	
	List<TestDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TestDO test);
	
	int update(TestDO test);
	
	int remove(Integer testId);
	
	int batchRemove(Integer[] testIds);
}
