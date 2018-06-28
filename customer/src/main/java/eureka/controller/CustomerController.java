package eureka.controller;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@RequestMapping("/getCustomer")
	public String getCustomer() {
		Log.info("获取用户");
		return ShowInCenter.showInCenter(GetTime.getTimeNow(), "用户一");
	}

	// 通过产品系统用户去获取产品
	@RequestMapping("/getProduct")
	public String getProduct() {
		Log.info("通过产品系统获取产品");
		this.loadBalancerClient.choose("EUREKA_CLIENT_PRODUCT1");
		String result = restTemplate.getForObject("http://EUREKA_CLIENT_PRODUCT1/getProduct", String.class);
		return ShowInCenter.showInCenter(result);
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
		List<ServiceInstance> list = discoveryClient.getInstances("EUREKA1");
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

	@RequestMapping("log-instance")
	public String logUserInstance() {
		ServiceInstance serviceInstance = this.loadBalancerClient.choose("EUREKA_CLIENT_PRODUCT1");
		// 打印当前请求选择的哪个节点
		return ShowInCenter.showInCenter((String.format("{%s}:{%s}:{%s}", serviceInstance.getServiceId(),
				serviceInstance.getHost(), serviceInstance.getPort())));
	}

}
