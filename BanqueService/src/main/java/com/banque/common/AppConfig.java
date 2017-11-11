package com.banque.common;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.banque.service.* , com.banque.dao.*")
public class AppConfig {
	
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
	public LocalSessionFactoryBean  localSessionFactoryBean(ApplicationContext context){
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(context.getBean(BANQUE_DATASOURCE_NAME, DataSource.class));
		
		localSessionFactoryBean.setPackagesToScan("com.banque.modele");
		
		Properties props=new Properties();
	    props.put(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());
	    props.put(AvailableSettings.CACHE_REGION_FACTORY, EhCacheRegionFactory.class.getName());
	    props.put(AvailableSettings.CACHE_PROVIDER_CONFIG, "ehcache.xml");
	    props.put(AvailableSettings.USE_QUERY_CACHE, "true");
	    props.put(AvailableSettings.SHOW_SQL, "true");
	    props.put(AvailableSettings.AUTOCOMMIT, "false");
	    localSessionFactoryBean.setHibernateProperties(props);
	    
	    return localSessionFactoryBean;
	}
	
	@Bean
	public SessionFactory sessionFactory(ApplicationContext context){
		return context.getBean(LocalSessionFactoryBean.class).getObject();
	}
	
	@Bean(name=TRANSACTION_MANAGER)
	public PlatformTransactionManager dbTransactionManager(ApplicationContext context){
		HibernateTransactionManager hibernateTrasactionManager = new HibernateTransactionManager();
		hibernateTrasactionManager.setSessionFactory(context.getBean(SESSION_FACTORY,SessionFactory.class));
		return hibernateTrasactionManager;
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}
	
}
