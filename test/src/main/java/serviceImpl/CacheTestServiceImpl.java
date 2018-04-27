package serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import pojo.User;
import service.CacheTestService;

@Service
@Transactional
@EnableCaching
@CacheConfig(cacheNames = "cache1,cache2", keyGenerator = "wiselyKeyGenerator")
public class CacheTestServiceImpl implements CacheTestService {

	@Autowired
	private UserDao userDao;

	@Override
	@Cacheable(key = "#user.id", condition = "#user.id.length() > 1")
	public User getUserById(User user) {
		return userDao.findOne(user.getId());
	}

}
