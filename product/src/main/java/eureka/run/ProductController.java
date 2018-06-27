package eureka.run;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eureka.utils.GetTime;
import eureka.utils.ShowInCenter;

@RestController
public class ProductController {

	@RequestMapping("/getProduct")
	public String getProduct() {
		return ShowInCenter.showInCenter(GetTime.getTimeNow()) + ShowInCenter.showInCenter("产品一");
	}

}
