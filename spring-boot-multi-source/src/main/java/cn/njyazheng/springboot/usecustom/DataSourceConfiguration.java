package cn.njyazheng.springboot.usecustom;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("custom")
@MapperScan(basePackages = "cn.njyazheng.springboot.mapper")
public class DataSourceConfiguration {
    @Bean("db01")
    public DataSource datasource1(Environment environment) throws SQLException {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName(environment.getProperty("spring.datasource.db01.driver-class-name"));
//        hikariDataSource.setJdbcUrl(environment.getProperty("spring.datasource.db01.url"));
//        hikariDataSource.setUsername(environment.getProperty("spring.datasource.db01.username"));
//        hikariDataSource.setPassword(environment.getProperty("spring.datasource.db01.password"));
//        return hikariDataSource;
        //2pc
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(environment.getProperty("spring.datasource.db01.url"));
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(environment.getProperty("spring.datasource.db01.password"));
        mysqlXaDataSource.setUser(environment.getProperty("spring.datasource.db01.username"));
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("db01");
        xaDataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.db01.min-pool-size")));
        xaDataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.db01.max-pool-size")));
        xaDataSource.setMaxLifetime(Integer.parseInt(environment.getProperty("spring.datasource.db01.max-life-time")));
        xaDataSource.setBorrowConnectionTimeout(Integer.parseInt(environment.getProperty("spring.datasource.db01.borrow-connectin-timeout")));
        xaDataSource.setLoginTimeout(Integer.parseInt(environment.getProperty("spring.datasource.db01.login-timeout")));
        xaDataSource.setMaintenanceInterval(Integer.parseInt(environment.getProperty("spring.datasource.db01.maintenance-interval")));
        xaDataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("spring.datasource.db01.maxi-idle-time")));
        xaDataSource.setTestQuery(environment.getProperty("spring.datasource.db01.testQuery"));
        return xaDataSource;
    }

    @Bean("db02")
    public DataSource datasource2(Environment environment) throws SQLException {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName(environment.getProperty("spring.datasource.db02.driver-class-name"));
//        hikariDataSource.setJdbcUrl(environment.getProperty("spring.datasource.db02.url"));
//        hikariDataSource.setUsername(environment.getProperty("spring.datasource.db02.username"));
//        hikariDataSource.setPassword(environment.getProperty("spring.datasource.db02.password"));
//        return hikariDataSource;
        //2pc
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(environment.getProperty("spring.datasource.db02.url"));
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(environment.getProperty("spring.datasource.db02.password"));
        mysqlXaDataSource.setUser(environment.getProperty("spring.datasource.db02.username"));
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("db02");
        xaDataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.db02.min-pool-size")));
        xaDataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("spring.datasource.db02.max-pool-size")));
        xaDataSource.setMaxLifetime(Integer.parseInt(environment.getProperty("spring.datasource.db02.max-life-time")));
        xaDataSource.setBorrowConnectionTimeout(Integer.parseInt(environment.getProperty("spring.datasource.db02.borrow-connectin-timeout")));
        xaDataSource.setLoginTimeout(Integer.parseInt(environment.getProperty("spring.datasource.db02.login-timeout")));
        xaDataSource.setMaintenanceInterval(Integer.parseInt(environment.getProperty("spring.datasource.db02.maintenance-interval")));
        xaDataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("spring.datasource.db02.maxi-idle-time")));
        xaDataSource.setTestQuery(environment.getProperty("spring.datasource.db02.testQuery"));
        return xaDataSource;

    }


    @Bean
    public RoutingDataSource routingDataSource(Environment environment) throws SQLException {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> datasources = new HashMap<>();
        datasources.put("db01", datasource1(environment));
        datasources.put("db02", datasource2(environment));
        routingDataSource.setTargetDataSources(datasources);
        routingDataSource.setDefaultTargetDataSource(datasource1(environment));
        return routingDataSource;
    }

    @Bean
    // 表示这个数据源是默认数据源
    //mybatis的相关配置
    //非分布式,创建一个即可
    public SqlSessionFactory sqlSessionFactory(RoutingDataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //事务工厂
        bean.setTransactionFactory(new MultiDataSourceTransactionFactory());
        //bean.setMapperLocations(
        // 设置mybatis的sql的mapper.xml所在位置
        //new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
        return bean.getObject();
    }

    //数据源事务
//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager(RoutingDataSource datasource) {
//        return new DataSourceTransactionManager(datasource);
//    }

    @Bean
    public SqlSessionTemplate test2sqlsessiontemplate(SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
