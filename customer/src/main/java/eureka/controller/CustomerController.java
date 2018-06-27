package eureka.controller;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import eureka.utils.GetTime;
import eureka.utils.ShowInCenter;

@RestController
public class CustomerController {

	final org.apache.commons.logging.Log Log = LogFactory.getLog(CustomerController.class);

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiscoveryClient discoveryClient;

	// 产品系统的地址
	final String ADDRESS = "http://localhost:8764/";
	// 获取产品的方法
	final String GETPRODUCT = "getProduct";

	@RequestMapping("/getCustomer")
	public String getCustomer() {
		Log.info("获取用户");
		return ShowInCenter.showInCenter(GetTime.getTimeNow()) + ShowInCenter.showInCenter("用户一");
	}

	// 通过产品系统用户去获取产品
	@RequestMapping("/getProduct")
	public String getProduct() {
		Log.info("通过产品系统获取产品");
		return ShowInCenter.showInCenter(restTemplate.getForObject(ADDRESS + GETPRODUCT, String.class));
	}

	@RequestMapping(value = "/getinstanceInfo")
	public String add() {
		ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		Log.info("获取本地实例的信息");
		return ShowInCenter.showInCenter(GetTime.getTimeNow()) + ShowInCenter.showInCenter("host:" + instance.getHost())
				+ ShowInCenter.showInCenter("serviceId:" + instance.getServiceId())
				+ ShowInCenter.showInCenter("metadata:" + instance.getMetadata());
	}

}
