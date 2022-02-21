package blog.benggri.springboot.config.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = "blog.benggri.springboot.jpa.repository" , entityManagerFactoryRef = "basicEntityManager", transactionManagerRef = "basicTransactionManager")
public class JpaDataSourceConfig {

    @Value("${benggri.springboot.datasource.jndi-name}")
    private String jndiName;

    @Value("${spring.profiles.active}")
    private String actviceProfiles;

    @Value("${benggri.springboot.datasource.url}")
    private String url;

    @Value("${benggri.springboot.datasource.username}")
    private String username;

    @Value("${benggri.springboot.datasource.password}")
    private String password;

    @Value("${benggri.springboot.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public LocalContainerEntityManagerFactoryBean basicEntityManager() throws SQLException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("spring.jpa.properties.hibernate.show_sql"         , true);
        properties.put("spring.jpa.properties.hibernate.format_sql"       , true);
        properties.put("spring.jpa.properties.hibernate.use_sql_comments" , true);
        properties.put("hibernate.dialect"                                , "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.put("hibernate.implicit_naming_strategy"               , "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"); // 네이밍
        properties.put("hibernate.physical_naming_strategy"               , "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"); // 네이밍(카멜케이스)

        em.setDataSource(basicDataSource());
        em.setPackagesToScan("blog.benggri.springboot.jpa.entity");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(properties);
        em.setPersistenceUnitName("basic");
        return em;
    }

    @Bean
    public DataSource basicDataSource() throws SQLException {
        if ("TEST".equals(actviceProfiles.toUpperCase())){ // 현재 active profile 이 TEST 일 경우 개별 datasource 셋팅을 기반으로 실행
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(url);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } else {
            JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
            return dataSourceLookup.getDataSource(jndiName);
        }
    }

    @Bean
    public PlatformTransactionManager basicTransactionManager() throws SQLException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(basicEntityManager().getObject());
        return transactionManager;
    }
}
