package configClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource("application.properties")
public class ConfigClientController {

	@Value("${profile}")
	private String profile;

	@RequestMapping("get")
	public String hello() {
		return this.profile;
	}
}
