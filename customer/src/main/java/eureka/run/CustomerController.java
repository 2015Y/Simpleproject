package eureka.run;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
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
	@Autowired
	private ProductFeignClient productFeignClient;

	@RequestMapping("/getCustomer")
	public String getCustomer() {
		Log.info("获取用户");
		return ShowInCenter.showInCenter(GetTime.getTimeNow(), "用户一");
	}

	// 通过产品系统用户去获取产品
	@RequestMapping("/getProduct")
	public String getProduct() {
		Log.info("通过产品系统获取产品");
		String result = false + "";
		try {
			result = restTemplate.getForObject("http://product/getProduct", String.class);
		} catch (Exception e) {
			Log.info(e.getMessage());
		}
		// return restTemplate.getForObject("http://localhost:8764/getProduct",
		// String.class);
		return result;
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
		StringBuilder sb = new StringBuilder();
		sb.append(ShowInCenter.showInCenter(GetTime.getTimeNow()));
		for (String s : discoveryClient.getServices()) {
			sb.append(ShowInCenter.showInCenter("services " + s));
			List<ServiceInstance> serviceInstances = discoveryClient.getInstances(s);
			for (ServiceInstance si : serviceInstances) {
				sb.append(ShowInCenter.showInCenter("---------------------------------------------------"));
				sb.append(ShowInCenter.showInCenter("    services:" + s + ":getHost()=" + si.getHost()));
				sb.append(ShowInCenter.showInCenter("    services:" + s + ":getPort()=" + si.getPort()));
				sb.append(ShowInCenter.showInCenter("    services:" + s + ":getServiceId()=" + si.getServiceId()));
				sb.append(ShowInCenter.showInCenter("    services:" + s + ":getUri()=" + si.getUri()));
				sb.append(ShowInCenter.showInCenter("    services:" + s + ":getMetadata()=" + si.getMetadata()));
				sb.append(ShowInCenter.showInCenter("---------------------------------------------------"));
			}

		}
		return sb.toString();
	}

	@RequestMapping("/log-instance")
	public String logUserInstance() {
		ServiceInstance serviceInstance = this.loadBalancerClient.choose("PRODUCT");
		// 打印当前请求选择的哪个节点
		return ShowInCenter.showInCenter((String.format("{%s}:{%s}:{%s}", serviceInstance.getServiceId(),
				serviceInstance.getHost(), serviceInstance.getPort())));
	}

	@RequestMapping("/testProductFeignClient")
	public String testProductFeignClient() {
		return ShowInCenter.showInCenter(productFeignClient.getProdct());
	}

	@RequestMapping("/productFeignClient/{id}")
	public String testProductFeignClientById(@PathVariable(value = "id") String id) {
		return ShowInCenter.showInCenter(productFeignClient.getOneProduct(id));
	}

	@RequestMapping("/getOneProductByPojo")
	public String getOneProductByPojo(String id) {
		return ShowInCenter.showInCenter(productFeignClient.getOneProduct(Integer.parseInt(id)));
	}

}
