package serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.UserDao;
import pojo.Columns;
import pojo.User;
import service.LoginService;

@Service
@Transactional
@EnableCaching
@CacheConfig(cacheNames = "user", keyGenerator = "wiselyKeyGenerator")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserDao userDao;

	public String login(String user) {
		int i = 0;
		i = Integer.parseInt(user);
		if (i % 2 == 0) {
			return "[" + user + "]	欢迎	!";
		}
		return "[" + user + "]	滚蛋	!";
	}

	public Map<String, Object> getUsers(Integer pageNumber, Integer pageSize) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
		Page<User> findAll = userDao.findAll(pageable);
		Map<String, Object> result = new HashMap<>();
		result.put("total", findAll.getTotalElements());
		result.put("rows", findAll.getContent());
		return result;
	}

	@Override
	public int addUser() {
		Object maxId = userDao.getMaxId();
		int count = Integer.parseInt(maxId + "");
		List<User> userList = new ArrayList<>();
		User u = null;
		long startTime = System.currentTimeMillis();
		for (int i = 1; i < 11; i++) {
			u = new User();
			u.setId(count + i + "");
			u.setName("name" + i);
			u.setAge(i + "");
			userList.add(u);
		}
		userDao.save(userList);
		long endTime = System.currentTimeMillis();
		return (int) (endTime - startTime) / 1000;
	}

	@Override
	public List<User> getAlluser() {
		return userDao.findAll();
	}

	@Override
	public Map<String, Object> getDynamicData(Integer pageNumber, Integer pageSize) {
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize);
		Page<User> findAll = userDao.findAll(pageable);
		Map<String, Object> result = new HashMap<>();
		result.put("total", findAll.getTotalElements());
		result.put("rows", findAll.getContent());
		return result;
	}

	@Override
	public Map<String, Object> getDynamicColumns() {
		Map<String, Object> map = new HashMap<>();
		List<Columns> list = new ArrayList<>();
		Columns col = new Columns();
		col.setField("id");
		col.setTitle("序列号");
		col.setWidth(200);
		col.setAlign("center");
		list.add(col);
		Columns col1 = new Columns();
		col1.setField("name");
		col1.setTitle("姓名");
		col1.setWidth(520);
		col1.setAlign("center");
		list.add(col1);
		map.put("columns", list);
		return map;
	}

	@Override
	public List<Map<String, Object>> getUsersByAge(Integer age) {
		return userDao.getUsersByAge(age);
	}

	@Override
	@Modifying
	@CachePut(key = "#user.id")
	public User updateOne(User user) {
		return userDao.save(user);

	}

	@Override
	@Cacheable(key = "#user.id")
	public User findOne(User user) {
		return userDao.findOne(user.getId());
	}

	@Transactional
	@Modifying
	@CacheEvict(key = "#user.id")
	public void deleteById(User user) {
		userDao.delete(user.getId());
	}

	@Override
	public void addOneUser(User user) {
		userDao.save(user);
	}

	@Override
	public void test(User user) {
		userDao.save(user);
		userDao.delete(user.getId());
	}

	@Override
	public List<User> findAge(String age) {
		
//		List<User> collect = userList.stream().filter(u -> u.getName().equals("name1")).collect(Collectors.toList()) ;
		return userDao.findByAge(age);
	};

}
