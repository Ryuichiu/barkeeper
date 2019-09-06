package de.barkeeper.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.barkeeper.repository")
@ComponentScan(basePackages = "de.barkeeper.repository")
public class DataSourceConfig {

    private String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DATABASE_HOST = "localhost";
    private int DATABASE_PORT = 3306;
    private String DATABASE_NAME = "barkeeper";
    private String DATABASE_USERNAME = "root";
    private String DATABASE_PASSWORD = "";

    /**
     * Interface provides a standard method of working with database connections.
     * Traditionally, a data source uses a URL along with some credentials to establish a database connection.
     *
     * @return
     */
    @Bean(name = "datasource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(MYSQL_JDBC_DRIVER);
        driverManagerDataSource.setUrl("jdbc:mysql://" + DATABASE_HOST + ":" + DATABASE_PORT + "/" + DATABASE_NAME + "?serverTimezone=UTC?");
        driverManagerDataSource.setUsername(DATABASE_USERNAME);
        driverManagerDataSource.setPassword(DATABASE_PASSWORD);
        return driverManagerDataSource;
    }

    /**
     * The purpose of the EntityManager is to interact with the persistence context will then manage entity instances
     *      and their associated lifecycle.
     *
     * Specify the JDBC data source that the JPA persistence provider is supposed to use for accessing the database.
     *
     * Set whether to scanning for entity classes in the classpath instead of using JPA's standard scanning of jar files
     *      with persistence.xml markers in them. In case of scanning, no persistence.xml is necessary; All to need to
     *      do is to specify base packages to search here.
     *
     * Expose Hibernate's persistence provider and EntityManager extension interface and adapts AbstractJpaVendorAdapter
     *      common configuration settings. Also support the detection of annotated packages which required no persistence.xml.
     *
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(new String[] {"de.barkeeper.model"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(hibernateProperties());
        return entityManagerFactory;
    }

    /**
     * Understanding transaction abstraction outlines the core classes and describes how to configure and obtain data source
     *      instances from a variety of sources. Synchronizing resources with transactions describes how the application
     *      code ensures that resources are created, reused and cleaned up properly.
     *
     * It extends the 'java.lang.RuntimeException' class.
     *
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Bean post-processor that automatically applies persistence exception translation to any bean marked with
     *      @Repository annotation, adding a corresponding PersistenceExceptionTranslationAdvisor to the exposed proxy
     *      (either an existing AOP proxy or a newly generated proxy that implements all of the target's interfaces).
     *
     * Translates native resource exceptions to DataAccessException hierarchy. Autodetect beans that implements the
     *      PersistenceExceptionTranslator interface, which are subsequently asked to translate candidate exceptions.
     *
     * All of applicable resource factories (e.g. LocalContainerEntityManagerFactoryBean) implement the
     *      PersistenceExceptionTranslator interface out of the box. As a consequence, all that is usually needed to
     *      enable automatic exception translation is marking all affected beans (such as Repositories or DAOs) with the
     *      @Repository annotation, along with defining this post-processor as a bean in the application context.
     *
     * @return
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Properties of Hibernate
     *      create, update, validate, create-drop
     *
     * @return
     */
    protected Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }
}
