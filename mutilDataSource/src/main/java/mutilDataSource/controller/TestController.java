package mutilDataSource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mutilDataSource.service.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping("/test")
	public Object test() {
		return testService.findAll();
	}

	@RequestMapping("/findOneUser/{id}")
	public Object findOneUser(@PathVariable("id") String id) throws Exception {
		return testService.findOneUser(id);
	}

	@RequestMapping("/findOneTest/{id}")
	public Object findOneTest(@PathVariable("id") String id) throws Exception {
		return testService.findOneTest(id);
	}

	@RequestMapping("/findOneThree/{id}")
	public Object findOneThree(@PathVariable("id") String id) throws Exception {
		return testService.findOneThree(id);
	}

	@RequestMapping("/testTransaction")
	public Object testTransaction() throws Exception {
		return testService.testTransaction();
	}

}
