package nifori.me.persistence;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "nifori.me.persistence.globalname",
    entityManagerFactoryRef = "globalnameEntityManager", transactionManagerRef = "globalnameTransactionManager")
public class GlobalNamePersistenceConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource.globalnamedb")
  public DataSourceProperties globalnamedbDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  public DataSource globalnamedbDataSource() {
    return globalnamedbDataSourceProperties().initializeDataSourceBuilder()
        .build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean globalnameEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(globalnamedbDataSource());
    em.setPackagesToScan("nifori.me.persistence.globalname");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "none");
    properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public PlatformTransactionManager globalnameTransactionManager() {

    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(globalnameEntityManager().getObject());
    return transactionManager;
  }
}
