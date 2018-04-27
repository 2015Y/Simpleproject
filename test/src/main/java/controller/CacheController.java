package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pojo.User;
import service.CacheTestService;

@Controller
public class CacheController {

	@Autowired
	private CacheTestService cacheTestService;

	@ResponseBody
	@RequestMapping("/findOneById")
	public User findOne(User user) {
		user.setName("0");
		user.setAge("0");
		return cacheTestService.getUserById(user);
	}
}
