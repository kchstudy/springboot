package blog.benggri.springboot.config.datasource;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@MapperScan(basePackages = "blog.benggri.springboot", annotationClass = Mapper.class, sqlSessionFactoryRef = "sqlSessionFactory")
public class JndiDataSourceConfig {

    @Value("${benggri.springboot.datasource.jndi-name}")
    private String jndiName;

    @Value("${spring.profiles.active}")
    private String actviceProfiles;

    @Bean
    @ConfigurationProperties(prefix = "benggri.springboot.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource(@Autowired @Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        if ("TEST".equals(actviceProfiles.toUpperCase())){ // 현재 active profile 이 TEST 일 경우 개별 datasource 셋팅을 기반으로 실행
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(dataSourceProperties.getUrl());
            dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
            dataSource.setUsername(dataSourceProperties.getUsername());
            dataSource.setPassword(dataSourceProperties.getPassword());
            return dataSource;
        } else {
            JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
            return dataSourceLookup.getDataSource(jndiName);
        }
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier("dataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(datasource);
        sqlSessionFactory.setConfigLocation( new PathMatchingResourcePatternResolver().getResource("classpath:static/config/mapper_config.xml") );
        sqlSessionFactory.setMapperLocations( new PathMatchingResourcePatternResolver().getResources("classpath*:static/mappers/**/*.xml") );
        return sqlSessionFactory.getObject();
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Autowired @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Autowired @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
