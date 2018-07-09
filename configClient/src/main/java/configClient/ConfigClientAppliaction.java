package configClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import configClient.interceptor.MyInterceptor;

@SpringBootApplication
public class ConfigClientAppliaction {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientAppliaction.class, args);
	}

	// mvc控制器
	@Configuration
	public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
		// 增加拦截器
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new MyInterceptor()) // 指定拦截器类
					.addPathPatterns("/get"); // 指定该类拦截的url
		}
	}

}
