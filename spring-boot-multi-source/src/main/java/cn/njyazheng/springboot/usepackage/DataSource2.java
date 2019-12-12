package cn.njyazheng.springboot.usepackage;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@Profile("package")
@MapperScan(basePackages = "cn.njyazheng.springboot.usepackage.db02",sqlSessionFactoryRef ="db02SqlSessionFactory" )
public class DataSource2 {
    @Bean("db02")
    public DataSource datasource2(Environment environment) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(environment.getProperty("spring.datasource.db02.driver-class-name"));
        hikariDataSource.setJdbcUrl(environment.getProperty("spring.datasource.db02.url"));
        hikariDataSource.setUsername(environment.getProperty("spring.datasource.db02.username"));
        hikariDataSource.setPassword(environment.getProperty("spring.datasource.db02.password"));
        return hikariDataSource;
    }

    @Bean(name = "db02SqlSessionFactory")
    // 表示这个数据源是默认数据源
    //mybatis的相关配置
    public SqlSessionFactory test1SqlSessionFactory(@Qualifier("db02") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //bean.setMapperLocations(
        // 设置mybatis的sql的mapper.xml所在位置
        //new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/test01/*.xml"));
        return bean.getObject();
    }

    //数据源事务
    @Bean(name = "db02TransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("db02") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }

    @Bean("db02SqlSessionTemplate")
    public SqlSessionTemplate test2sqlsessiontemplate(
            @Qualifier("db02SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
