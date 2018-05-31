package mutilDataSource.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mutilDataSource.dao.cluster.TestDao;
import mutilDataSource.dao.master.UserDao;
import mutilDataSource.dao.three.ThreeDao;
import mutilDataSource.service.TestService;

@Service
public class TestserviceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ThreeDao threeDao;

	@Override
	@Transactional(value="transactionManager",rollbackFor = Exception.class)
	public int testTransaction() throws Exception {
		int a = testDao.addOne();
		int b = threeDao.addOne();
		int c = userDao.addOne();
		return (a + b+c);
	}

	@Override
	@Transactional(value="transactionManager",rollbackFor = Exception.class)
	public Map<String, Object> findAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("db1_test", testDao.findAll());
		map.put("s_user", userDao.findAll());
		map.put("db2_test", threeDao.findAll());
		return map;
	}

	@Override
	@Transactional(value="masterTransactionManager",rollbackFor = Exception.class)
	public Map<String, Object> findOneUser(String id) {
		return userDao.findOne(id);
	}

	@Override
	@Transactional(value="clusterTransactionManager",rollbackFor = Exception.class)
	public Map<String, Object> findOneTest(String id) {
		return testDao.findOne(id);
	}

	@Override
	@Transactional(value="ThreeTransactionManager",rollbackFor = Exception.class)
	public Map<String, Object> findOneThree(String id) {
		return threeDao.findOne(id);
	}

}
