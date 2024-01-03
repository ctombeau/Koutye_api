package com.chrisnor.koutye.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "KoutyeEntityManager",
transactionManagerRef = "KoutyeTransactionManager",
basePackages="com.chrisnor.koutye")
@EnableAutoConfiguration
public class KoutyeConfigDatabase {

	 @Value("${spring.datasource.driver-class-name}")
	 private String driver;
	 
	 @Value("${spring.datasource.url}")
	 private String url;
	    
	 @Value("${spring.datasource.username}")
	 private String username;
	    
	 @Value("${spring.datasource.password}")
	 private String password;
	    
	 @Value("${entitymanager.packages.to.scan}")
	 private String packageScan;
	    
	 @Bean(name = "koutyeDataSource")
	 @ConfigurationProperties(prefix="spring.datasource")
	 
	public DataSource koutyeDataSource() { 
	    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 
	    dataSourceBuilder.driverClassName(driver);
	    dataSourceBuilder.url(url);
	    dataSourceBuilder.username(username); 
	    dataSourceBuilder.password(password); // System.out.println ("Le mot de passe est :"+ password);
	    return dataSourceBuilder.build(); 
	}
	@Bean(name = "KoutyeEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean KoutyeEntityManager() {
     LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
     em.setDataSource(koutyeDataSource());
     em.setPackagesToScan("com.chrisnor.koutye.model");
     HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
     em.setJpaVendorAdapter(vendorAdapter);
     em.setJpaProperties(hibernateProperties());

    return em;
 
 }
 
  @Primary
  @Bean(name = "KoutyeTransactionManager")
  public PlatformTransactionManager KoutyeTransactionManager( @Qualifier("KoutyeEntityManager") EntityManagerFactory KoutyeEntityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(KoutyeEntityManager().getObject());
    return transactionManager;
   //  return new JpaTransactionManager(PwsEntityManagerFactory);
 }

 @Bean(name = "KoutyeSessionFactory")
 public LocalSessionFactoryBean PwsSessionFactory() {
     LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
     sessionFactoryBean.setDataSource(koutyeDataSource());
   
     sessionFactoryBean.setHibernateProperties(hibernateProperties());
     return sessionFactoryBean;
}

 private Properties hibernateProperties() {
     Properties properties = new Properties();
     properties.put("hibernate.jdbc.time_zone", "UTC");
     properties.put("hibernate.hbm2ddl.auto","none");
     properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
     properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
     properties.put("hibernate.show_sql","false");
     properties.put("hibernate.format_sql","true");
     properties.put("entitymanager.packages.to.scan",packageScan);
   
     return properties;
 }	 

}
