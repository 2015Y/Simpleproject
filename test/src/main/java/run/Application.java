package run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration
@EntityScan("pojo")
@EnableJpaRepositories("dao")
@EnableTransactionManagement
@ComponentScan(value = { "controller", "service", "serviceImpl", "config", "rabbitMQ" })
public class Application extends SpringBootServletInitializer {

	// test
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		log.info(
				"springboot	....................................................................................................................................");
		SpringApplication.run(Application.class, args);
	}

}
