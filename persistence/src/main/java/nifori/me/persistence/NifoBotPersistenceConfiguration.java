package nifori.me.persistence;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Configuration
@EnableJpaRepositories(basePackages = "nifori.me.persistence.nifobot", entityManagerFactoryRef = "nifobotEntityManager",
    transactionManagerRef = "nifobotTransactionManager")
@Log4j2
public class NifoBotPersistenceConfiguration {

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource.nifobot")
  public DataSourceProperties nifobotDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  public DataSource nifobotDataSource() {
    return nifobotDataSourceProperties().initializeDataSourceBuilder()
        .build();
  }

  @Bean
  @Primary
  @SneakyThrows
  public LocalContainerEntityManagerFactoryBean nifobotEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    DataSource ds = nifobotDataSource();
    em.setDataSource(ds);
    em.setPackagesToScan("nifori.me.persistence.nifobot");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    HashMap<String, Object> properties = new HashMap<>();

    switch (ds.getConnection().getMetaData().getDatabaseProductName()){
      case "H2":
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        break;
      default:
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
        break;
    }

    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  @Primary
  public PlatformTransactionManager nifobotTransactionManager() {

    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(nifobotEntityManager().getObject());
    return transactionManager;
  }
}
