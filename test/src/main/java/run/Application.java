package run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (container -> {
			 ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED,"/403");
			 ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND,"/404");
			 ErrorPage error500Page = new
			 ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/Error");
			container.addErrorPages(error401Page, error404Page, error500Page);
			container.setSessionTimeout(1800);// 单位为S
		});
	}
	
}
