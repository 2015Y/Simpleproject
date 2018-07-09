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
			// 指定拦截器类
			registry.addInterceptor(new MyInterceptor()).addPathPatterns("/*");
			// 指定该类拦截的URL 指定放行的URL
			// .addPathPatterns("/get").excludePathPatterns("/get1");
		}
	}

}
