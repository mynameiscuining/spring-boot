package cn.njyazheng.springboot.cutstom;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.   //认证过的请求 ,访问以/admin的url需要管理员权限
                authorizeRequests().antMatchers("/admin/**").access("hasRole('admin')")
                .and()
                //开启readme功能,有效时间为1天参数单位秒,认证存储Cookie,以键值remember-me-key保存,保存前会以MD5加密
                .rememberMe().tokenValiditySeconds(86400).key("remember-me-key")
                .and()
                //启用http基础功能
                .httpBasic()
                .and()
                //认证过的请求,可以访问任何请求
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                 //表单认证 登录
                .formLogin()
                 //登录地址
                .loginPage("/custom/login/page")
                //登录成功后,跳转地址
                .defaultSuccessUrl("/admin/welcom");
                
              
    }
}
