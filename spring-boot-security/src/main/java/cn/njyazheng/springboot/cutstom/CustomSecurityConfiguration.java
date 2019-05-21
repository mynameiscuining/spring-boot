package cn.njyazheng.springboot.cutstom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
    //密码编码器
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        //使用内存存储
        auth.inMemoryAuthentication()
                //设置密码编码器
                .passwordEncoder(passwordEncoder())
                //注册用户
                .withUser("admin")
                // 设置密码
                .password(passwordEncoder().encode("abc"))
                .roles("USER", "ADMIN")
                //连接方法
                .and()
                //注册用户
                .withUser("root")
                // 设置密码
                .password(passwordEncoder().encode("abd"))
                .roles("USER");
        
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.   //认证过的请求 ,访问以/admin的url需要管理员权限
                authorizeRequests().antMatchers("/custom/admin/**").access("hasRole('ADMIN')")
                .and()
                //开启readme功能,有效时间为1天参数单位秒,认证存储Cookie,以键值remember-me-key保存,保存前会以MD5加密
                .rememberMe().tokenValiditySeconds(86400).key("remember-me-key")
                .and()
                //启用http基础功能
                .httpBasic()
                //设置模态对话框标题
                .realmName("this-is-realm-name")
                .and()
                //认证过的请求,可以访问任何请求
                .authorizeRequests().antMatchers("/custom/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                //表单认证 登录
                .formLogin()
                //登录地址
                .loginPage("/custom/login/page")
                //登录成功后,跳转地址
                .defaultSuccessUrl("/custom/admin/welcome")
                .and().logout().logoutUrl("/custom/logout/")
                .logoutSuccessUrl("/custom/login/page")
                .and().csrf().disable();
    }
}
