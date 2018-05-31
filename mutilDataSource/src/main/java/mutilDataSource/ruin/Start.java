package mutilDataSource.ruin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan({ "mutilDataSource.*", "mutilDataSource.service.impl" }) // 包名
public class Start {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) {
		SpringApplication.run(Start.class, args);
		System.out.println(
				"*************************************bootdo-start-success*************************************");
	}

}