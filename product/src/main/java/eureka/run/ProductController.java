package eureka.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eureka.utils.GetTime;
import eureka.utils.ShowInCenter;

@RestController
public class ProductController {

	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/getProduct")
	public String getProduct() {
		return ShowInCenter.showInCenter(String.valueOf("端口号:" + discoveryClient.getLocalServiceInstance().getPort()),
				GetTime.getTimeNow(), "产品一");
	}

	@RequestMapping("/getProduct/{id}")
	public String getProductById(@PathVariable(value = "id") String id) {
		return ShowInCenter.showInCenter(String.valueOf("端口号:" + discoveryClient.getLocalServiceInstance().getPort()),
				GetTime.getTimeNow(), "产品:" + id);
	}

	@RequestMapping("/getOneProduct/{id}")
	public String getOneProduct(@PathVariable(value = "id") String id) {
		Integer newId = 0;
		try {
			newId = Integer.parseInt(id);
		} catch (Exception e) {
		}
		Product findOne = productRepository.findOne(newId);
		return ShowInCenter.showInCenter(findOne == null ? "没有Id为【" + newId + "】的商品" : findOne.toString());
	}

	@RequestMapping("/getOneProductByPojo")
	public String getOneProductByPojo(Product p) {
		Product findOne = productRepository.findOne(p.getId());
		return ShowInCenter.showInCenter(findOne == null ? "没有Id为【" + p.getId() + "】的商品" : findOne.toString());
	}

}
