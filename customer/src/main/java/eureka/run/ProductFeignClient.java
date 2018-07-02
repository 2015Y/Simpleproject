package eureka.run;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "product")
public interface ProductFeignClient {

	@RequestMapping("getProduct")
	String getProdct();

	@RequestMapping("getProduct/{id}")
	String getProdct(@PathVariable(value = "id") String id);

	@RequestMapping("getOneProduct/{id}")
	String getOneProduct(@PathVariable(value = "id") String id);

	@RequestMapping("getOneProductByPojo")
	String getOneProduct(@RequestBody Product p);

}
