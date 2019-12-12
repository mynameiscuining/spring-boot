package cn.njyazheng.springboot.usepackage;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile("package")
@MapperScan(basePackages = "cn.njyazheng.springboot.usepackage.db01", sqlSessionFactoryRef = "db01SqlSessionFactory")
public class DataSource1 {
    @Bean("db01")
    @Primary
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

    @Bean(name = "db01SqlSessionFactory")
    // 表示这个数据源是默认数据源
    //mybatis的相关配置
    @Primary
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("db01") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //bean.setMapperLocations(
        // 设置mybatis的sql的mapper.xml所在位置
        //new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
        return bean.getObject();
    }

    //数据源事务
//    @Primary
//    @Bean(name = "db01TransactionManager")
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("db01") DataSource datasource) {
//        return new DataSourceTransactionManager(datasource);
//    }

    //sqlsession模板
    @Bean("db01SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate test2sqlsessiontemplate(
            @Qualifier("db01SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
