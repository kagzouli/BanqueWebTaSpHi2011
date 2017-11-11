package com.banque.common;

import org.springframework.context.annotation.ComponentScans;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;
import com.banque.modele.Parametre;
import com.banque.modele.Role;
import com.banque.modele.User;



@Configuration
@EnableTransactionManagement
@ComponentScans(value = { 
	      @ComponentScan("com.banque.service"),
	      @ComponentScan("com.banque.dao"),
	      @ComponentScan("com.banque.common") 
	    })
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
		
		localSessionFactoryBean.setAnnotatedClasses(Parametre.class, Role.class, User.class, EtatCompte. class , OperationCompte.class);
		
		Properties props=new Properties();
	    props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	    props.put("hibernate.cache.region.factory_class", EhCacheRegionFactory.class.getName());
	    props.put("hibernate.cache.provider_configuration_file_resource_path", "ehcache.xml");
	    props.put("hibernate.cache.use_query_cache", "true");
	    props.put("hibernate.show_sql", "true");
	    props.put("connection.autocommit", "false");
	    props.put("hibernate.hbm2ddl.auto", "create");
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
	
	/*

	<tx:advice id="txAdviceTestFront" transaction-manager="txTestManager">
		<tx:attributes>
			<tx:method name="find*" propagation="REQUIRED" read-only="true" />
			<tx:method name="*" propagation="REQUIRED"
				rollback-for="com.banque.service.exception.BusinessException,com.banque.service.exception.TechnicalException" />
		</tx:attributes>
	</tx:advice>


	<aop:config>
		<aop:pointcut id="banqueServiceMethods"
			expression="execution(* com.banque.service.impl.*BanqueImpl.*(..))" />
		<aop:advisor advice-ref="txAdviceTestFront" pointcut-ref="banqueServiceMethods" />
	</aop:config>

	<aop:config>
		<aop:pointcut id="banqueUserServiceMethods"
			expression="execution(* com.banque.service.impl.*UserServiceImpl.*(..))" />
		<aop:advisor advice-ref="txAdviceTestFront" pointcut-ref="banqueUserServiceMethods" />
	</aop:config>

	<!--  EH CACHE + SPRING -->
	<bean id="cacheManager"
		class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
		<property name="configLocation" value="classpath:ehcache.xml" />
	</bean>

	<bean id="autoproxy"
		class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator" /> */


}
