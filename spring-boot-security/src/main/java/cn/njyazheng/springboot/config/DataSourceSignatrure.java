package cn.njyazheng.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

//@Configuration
//使用数据库签名验证
public class DataSourceSignatrure extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceSignatrure.class);
    //注入数据源
    @Autowired
    private DataSource dataSource;
    @Value("${spring.security.secrit}")
    private String secrit;
    //使用用户名查询密码
    String pwdQuery = "select user_name,pwd,available from t_user where user_name=? ";
    String roleQuery = "select u.user_name,r.role_name from t_user u,t_user_role ur ,t_role r " +
            "where u.id= ur.user_id and  r.id=ur.role_id and u.user_name=? ";
    
    //用户签名构造器
    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码编码器
//        PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
        //秘钥密码编码器
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secrit);
        LOGGER.info("DataSourceSignatrure password abc--->" + passwordEncoder.encode("abc"));
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
    
    
    //限制资源请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //---------------------------------------------------------
        
        http.requiresChannel()//方法说明使用通道
                //匹配uri 使用https访问
                .antMatchers("/user/welcome", "/user/details").requiresSecure();
        //---------------------------------------------------------
        http.requiresChannel()
                //不使用https访问
                .antMatchers("/admin/**").requiresInsecure();
        //---------------------------------------------------------
        http.authorizeRequests()//开启请求权限配置
                //所有的请求 ,认证
                // .anyRequest().authenticated();
                //ant设置 角色ROLE_USER,ROLE_ADMIN可访问的资源,会加前缀ROLE_
                .antMatchers("/user/welcome", "/user/details").hasAnyRole("USER", "ADMIN")
                //正则设置  角色ROLE_ADMIN可访问的资源
                .regexMatchers("/admin/.*").hasAnyAuthority("ROLE_ADMIN")
                //支持spEL
                .antMatchers("/user/**").access("hasAnyAuthority('ROLE_USER')&&isFullyAuthenticated()")
                //其他任何资源请求不做认证
                .anyRequest().permitAll();
        //---------------------------------------------------------
        //资源没有权限的做匿名访问
        http.anonymous();
        //---------------------------------------------------------
        http.formLogin();//表单登录
        //---------------------------------------------------------
        http.httpBasic();//http基础认证
        //---------------------------------------------------------
        //默认启用, 防止csrf
        //关闭跨站访问
        http.csrf().disable();
        //---------------------------------------------------------
    }
}
