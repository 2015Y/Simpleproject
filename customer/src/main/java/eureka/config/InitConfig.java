package eureka.config;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eureka.utils.LogUtil;

@Configuration
public class InitConfig {

	final Log log = LogUtil.getLog(InitConfig.class);

	@Bean
	public HashMap<String, Object> getMap() {
		log.info("初始化一个map");
		return new HashMap<>();
	}

}
