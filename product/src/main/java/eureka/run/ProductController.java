package eureka.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eureka.utils.GetTime;
import eureka.utils.ShowInCenter;

@RestController
public class ProductController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/getProduct")
	public String getProduct() {
		return ShowInCenter.showInCenter(String.valueOf("端口号:" + discoveryClient.getLocalServiceInstance().getPort()))
				+ ShowInCenter.showInCenter(GetTime.getTimeNow()) + ShowInCenter.showInCenter("产品一");
	}

}
