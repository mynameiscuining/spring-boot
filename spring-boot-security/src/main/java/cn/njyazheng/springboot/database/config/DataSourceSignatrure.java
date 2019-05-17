package cn.njyazheng.springboot.database.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

@Configuration
//使用数据库签名验证
public class DataSourceSignatrure extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceSignatrure.class);
    //注入数据源
    @Autowired
    private DataSource dataSource;
    @Value("${spring.security.secrit}")
    private String secrit;
    //使用用户名查询密码
    String pwdQuery="select user_name,pwd,available from t_user where user_name=? ";
    String roleQuery="select u.user_name,r.role_name from t_user u,t_user_role ur ,t_role r " +
            "where u.id= ur.user_id and  r.id=ur.role_id and u.user_name=? ";
    
    //用户签名构造器
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码编码器
//        PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
        //秘钥密码编码器
        PasswordEncoder passwordEncoder =new Pbkdf2PasswordEncoder(secrit);
        LOGGER.info("DataSourceSignatrure password abc--->"+passwordEncoder.encode("abc"));
        //数据库储存
        auth.jdbcAuthentication()
                 //密码编码器
                .passwordEncoder(passwordEncoder)
                //数据源
                .dataSource(dataSource)
                //用户信息
                .usersByUsernameQuery(pwdQuery)
                //权限
                .authoritiesByUsernameQuery(roleQuery);
    }
}
