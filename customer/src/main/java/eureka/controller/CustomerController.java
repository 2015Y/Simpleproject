package eureka.controller;

import java.util.List;

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
	private String ADDRESS = "http://localhost:8764/";
	// private String ADDRESS = "http://eureka_client_product/";
	// 获取产品的方法
	private String GETPRODUCT = "getProduct";

	@RequestMapping("/getCustomer")
	public String getCustomer() {
		Log.info("获取用户");
		return ShowInCenter.showInCenter(GetTime.getTimeNow(), "用户一");
	}

	// 通过产品系统用户去获取产品
	@RequestMapping("/getProduct")
	public String getProduct() {
		Log.info("通过产品系统获取产品");
		List<String> services = discoveryClient.getServices();
		return ShowInCenter.showInCenter("获取到的服务信息:" + services == null ? "" : services.toString())
				+ restTemplate.getForObject(ADDRESS + GETPRODUCT, String.class);
	}

	@RequestMapping(value = "/getinstanceInfo")
	public String add() {
		ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		Log.info("获取本地实例的信息");
		return ShowInCenter.showInCenter(GetTime.getTimeNow(), "host:" + instance.getHost(),
				"serviceId:" + instance.getServiceId(), "metadata:" + instance.getMetadata());
	}

	@RequestMapping("/registered")
	public String getRegistered() {
		List<ServiceInstance> list = discoveryClient.getInstances("eureka_client_customer1");
		System.out.println(discoveryClient.getLocalServiceInstance());
		System.out.println("discoveryClient.getServices().size() = " + discoveryClient.getServices().size());

		for (String s : discoveryClient.getServices()) {
			System.out.println("services " + s);
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances(s);
			for (ServiceInstance si : serviceInstances) {
				System.out.println("    services:" + s + ":getHost()=" + si.getHost());
				System.out.println("    services:" + s + ":getPort()=" + si.getPort());
				System.out.println("    services:" + s + ":getServiceId()=" + si.getServiceId());
				System.out.println("    services:" + s + ":getUri()=" + si.getUri());
				System.out.println("    services:" + s + ":getMetadata()=" + si.getMetadata());
			}

		}

		System.out.println(list.size());
		if (list != null && list.size() > 0) {
			System.out.println(list.get(0).getUri());
		}
		return ShowInCenter.showInCenter("OK");
	}

}
