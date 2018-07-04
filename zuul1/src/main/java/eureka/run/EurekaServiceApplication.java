package eureka.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.netflix.zuul.ZuulFilter;

import eureka.run.filter.MyZuulFilter;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class EurekaServiceApplication {

	@Bean
	public ZuulFilter myZuulFilter() {
		return new MyZuulFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceApplication.class, args);
	}

}
