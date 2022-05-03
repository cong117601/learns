package com.cgm.dataSourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = SavleDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "salveSqlSessionFactory")
public class SavleDataSourceConfig {


    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.cgm.dao.salve";
    static final String MAPPER_LOCATION = "classpath:mapping/salve/*.xml";

    @Value("${spring.salve.datasource.url}")
    private String url;

    @Value("${spring.salve.datasource.username}")
    private String user;

    @Value("${spring.salve.datasource.password}")
    private String password;

    @Value("${spring.salve.datasource.driver-class-name}")
    private String driverClass;

    @Bean(name = "salveDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "salveTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }

    @Bean(name = "salveSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("salveDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(SavleDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}