package com.banque.common;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.banque.service.* , com.banque.dao.*")
@PropertySource(value = { "classpath:confighibernate.properties" })
public class AppConfig {
	
	@Autowired
	private Environment environment;
	
	/** Jndi banque name **/
	private static final String JNDI_BANQUE_NAME = "java:comp/env/jdbc/banque";
	
	private static final String BANQUE_DATASOURCE_NAME = "banqueDatasource";
	
	private static final String SESSION_FACTORY = "sessionFactory";
	
	public static final String TRANSACTION_MANAGER = "txTestManager";	
	
	
	@Bean(name=BANQUE_DATASOURCE_NAME)
	public DataSource banqueDataSource(){
		JndiDataSourceLookup datasourceLookup = new JndiDataSourceLookup();
		return datasourceLookup.getDataSource(JNDI_BANQUE_NAME);
	}
	
	@Bean(name=SESSION_FACTORY)
	public LocalContainerEntityManagerFactoryBean   entityManagerFactory(ApplicationContext context){
		LocalContainerEntityManagerFactoryBean localEntityFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localEntityFactoryBean.setDataSource(context.getBean(BANQUE_DATASOURCE_NAME, DataSource.class));
		localEntityFactoryBean.setPackagesToScan("com.banque.modele");		
		localEntityFactoryBean.setJpaVendorAdapter(context.getBean(JpaVendorAdapter.class));
		localEntityFactoryBean.setJpaProperties(this.hibernateProperties());
	    
	    return localEntityFactoryBean;
	}
	
	 @Bean
	 public JpaVendorAdapter jpaVendorAdapter() {
		 JpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
	     return hibernateJpaVendorAdapter;
	 }
	
	/**
	 * Set the hibernate properties
	 * 
	 * @return
	 */
	private Properties hibernateProperties(){
		Properties props=new Properties();
	    props.put(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());
	    props.put(AvailableSettings.CACHE_REGION_FACTORY, EhCacheRegionFactory.class.getName());
	    props.put(AvailableSettings.CACHE_PROVIDER_CONFIG, this.environment.getRequiredProperty("hibernate.cache.provider_configuration_file_resource_path"));
	    props.put(AvailableSettings.USE_QUERY_CACHE, this.environment.getRequiredProperty("hibernate.use.query.cache"));
	    props.put(AvailableSettings.SHOW_SQL, this.environment.getRequiredProperty("hibernate.showsql"));
	    props.put(AvailableSettings.AUTOCOMMIT, this.environment.getRequiredProperty("hibernate.autocommit"));
	    return props;
	}
	
	//////////////////// BEGIN Hibernate Config ///////////////////////////////////////////////////////////////////////
	/*@Bean(name=TRANSACTION_MANAGER)
	public PlatformTransactionManager dbTransactionManager(ApplicationContext context){
		HibernateTransactionManager hibernateTrasactionManager = new HibernateTransactionManager();
		hibernateTrasactionManager.setSessionFactory(context.getBean(SESSION_FACTORY,SessionFactory.class));
		return hibernateTrasactionManager;
	}
	
	@Bean
	public SessionFactory sessionFactory(ApplicationContext context){
		return context.getBean(LocalSessionFactoryBean.class).getObject();
	}
	
	@Bean(name=SESSION_FACTORY)
	public LocalSessionFactoryBean  localSessionFactoryBean(ApplicationContext context){
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(context.getBean(BANQUE_DATASOURCE_NAME, DataSource.class));
		localSessionFactoryBean.setPackagesToScan("com.banque.modele");		
	    localSessionFactoryBean.setHibernateProperties(this.hibernateProperties());
	    
	    return localSessionFactoryBean;
	}

	
	*/
	/////////////////// END Hibernate Config /////////////////////////////////////////////////////////////////////////
	
	@Bean(name=TRANSACTION_MANAGER)
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(emf);
	    return txManager;
	 }

	
	
	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}
	
}
