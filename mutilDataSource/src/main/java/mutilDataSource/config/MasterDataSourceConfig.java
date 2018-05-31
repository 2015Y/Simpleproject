package mutilDataSource.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.xa.DruidXADataSource;

import mutilDataSource.constant.Constants;;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

	private Logger logger = LoggerFactory.getLogger(MasterDataSourceConfig.class);

	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "mutilDataSource.dao.master";
	static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

	@Value("${spring.jta.atomikos.datasource.oneData.url}")
	private String url;
	@Value("${spring.jta.atomikos.datasource.oneData.username}")
	private String userName;
	@Value("${spring.jta.atomikos.datasource.oneData.password}")
	private String passWord;
	@Value("${spring.jta.atomikos.datasource.oneData.driverClassName}")
	private String driverClassName;

	@Primary
	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.oneData")
	public DataSource masterDataSource() {
		logger.info("init masterDataSource .....................................");
		// return DataSourceBuilder.create().build();
		AtomikosDataSourceBean masterBean = getAtomikosDataSourceBean();
		// 配置数据源连接池的信息
		masterBean.setXaDataSource(getDruidXADataSource());
		return masterBean;
	}

	@Primary
	@Bean(name = "masterTransactionManager")
	public DataSourceTransactionManager masterTransactionManager() {
		logger.info("intit masterTransactionManager.....................................");
		return new DataSourceTransactionManager(masterDataSource());
	}

	@Primary
	@Bean(name = "masterSqlSessionFactory")
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(masterDataSource);
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}

	private AtomikosDataSourceBean getAtomikosDataSourceBean() {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setPoolSize(Constants.POOLSIZE);
		atomikosDataSourceBean.setMinPoolSize(Constants.MINPOOLSIZE);
		atomikosDataSourceBean.setMaxPoolSize(Constants.MAXPOOLSIZE);
		atomikosDataSourceBean.setMaxLifetime(Constants.MAXIDLETIME);
		atomikosDataSourceBean.setBorrowConnectionTimeout(Constants.BORROWCONNECTIONTIMEOUT);
		atomikosDataSourceBean.setReapTimeout(Constants.REAPTIMEOUT);
		atomikosDataSourceBean.setMaxIdleTime(Constants.MAXIDLETIME);
		atomikosDataSourceBean.setXaDataSourceClassName(Constants.DRIVERCLASSNAME);
		return atomikosDataSourceBean;
	}

	private DruidXADataSource getDruidXADataSource() {
		DruidXADataSource druidXADataSource = new DruidXADataSource();
		druidXADataSource.setUrl(url);
		druidXADataSource.setUsername(userName);
		druidXADataSource.setPassword(passWord);
		druidXADataSource.setDriverClassName(driverClassName);
		druidXADataSource.setInitialSize(Constants.MINPOOLSIZE);
		druidXADataSource.setMinIdle(Constants.MINPOOLSIZE);
		return druidXADataSource;
	}

}