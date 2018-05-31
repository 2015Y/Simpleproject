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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.xa.DruidXADataSource;

import mutilDataSource.constant.Constants;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

	private Logger logger = LoggerFactory.getLogger(ClusterDataSourceConfig.class);

	// 精确到 cluster 目录，以便跟其他数据源隔离
	static final String PACKAGE = "mutilDataSource.dao.cluster";
	static final String MAPPER_LOCATION = "classpath:mapper/cluster/*.xml";

	@Value("${spring.jta.atomikos.datasource.twoData.url}")
	private String url;
	@Value("${spring.jta.atomikos.datasource.twoData.username}")
	private String userName;
	@Value("${spring.jta.atomikos.datasource.twoData.password}")
	private String passWord;
	@Value("${spring.jta.atomikos.datasource.twoData.driverClassName}")
	private String driverClassName;

	@Bean(name = "clusterDataSource")
	@ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.twoData")
	public DataSource clusterDataSource() {
		logger.info("init masterDataSource .....................................");
		// return DataSourceBuilder.create().build();
		AtomikosDataSourceBean clusterBean = getAtomikosDataSourceBean();
		// 配置数据源连接池的信息
		clusterBean.setXaDataSource(getDruidXADataSource());
		return clusterBean;
	}

	@Bean(name = "clusterTransactionManager")
	public PlatformTransactionManager clusterTransactionManager() {
		logger.info("init clusterTransactionManager.....................................");
		return new DataSourceTransactionManager(clusterDataSource());
	}

	@Bean(name = "clusterSqlSessionFactory")
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(clusterDataSource);
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
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
		return druidXADataSource;
	}

}