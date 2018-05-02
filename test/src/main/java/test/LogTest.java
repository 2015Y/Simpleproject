package test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import pojo.User;
import run.Application;
import service.LoginService;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class LogTest {

	@Autowired
	private LoginService loginService ;
	
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void test() {
		log.trace("trace的信息:{}", "日志输出等级为trace.");
		log.debug("debug的信息:{}", "日志输出等级为debug.");
		log.info("info的信息:{}", "日志输出等级为info.");
		log.error("error的信息:{}", "日志输出等级为error.");
		log.warn("warn的信息:{}", "日志输出等级为warn.");
		Assert.assertNotEquals(0, 1);
	}
	
	@Test
	public void loginTest() {
		String age = "0" ;
		List<User> users = loginService.findAge(age);
		long count = users.stream().filter(e -> e.getAge().equals(age)).count();
		log.info("年龄在{}岁的有{}人.",age,count);
		Assert.assertNotEquals(0, count);
	}
	
	@Test
	public void redisTest() {
		stringRedisTemplate.opsForValue().set("url", "www.lrshuai.top");
		stringRedisTemplate.expire("url", 30, TimeUnit.SECONDS);
		stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间  
		stringRedisTemplate.boundValueOps("test").increment(-1);//val做-1操作  
		stringRedisTemplate.opsForValue().get("test");//根据key获取缓存中的val  
		stringRedisTemplate.boundValueOps("test").increment(1);//val +1  
		stringRedisTemplate.getExpire("test");//根据key获取过期时间  
		stringRedisTemplate.getExpire("test",TimeUnit.SECONDS);//根据key获取过期时间并换算成指定单位  
		stringRedisTemplate.delete("test");//根据key删除缓存  
		stringRedisTemplate.hasKey("546545");//检查key是否存在，返回boolean值  
		stringRedisTemplate.opsForSet().add("red_123", "1","2","3","1");//向指定key中存放set集合  
		stringRedisTemplate.expire("red_123",1000*100000 , TimeUnit.MILLISECONDS);//设置过期时间  
		stringRedisTemplate.opsForSet().isMember("red_123", "1");//根据key查看集合中是否存在指定数据  
		stringRedisTemplate.opsForSet().members("red_123");//根据key获取set集合  
		Long expire100 = stringRedisTemplate.getExpire("100",TimeUnit.SECONDS);
		stringRedisTemplate.delete("red_123");//根据key删除缓存  
		Long expire = stringRedisTemplate.getExpire("red_123",TimeUnit.SECONDS);
		log.info("key:red_123过期的时间{}s.",expire);
		log.info("key:100过期的时间{}s.",expire100);
		log.info("------OK------");
	}
	
}
