package cn.njyazheng.springboot.database.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//使用内存签名服务
//就是将用户信息放进内存中
//@Configuration
public class MemorySignature extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码编码器
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //使用内存存储
        auth.inMemoryAuthentication()
                //设置密码编码器
                .passwordEncoder(passwordEncoder)
                //注册用户
                .withUser("admin")
                // 设置密码
                .password(passwordEncoder.encode("abc"))
                .roles("USER", "ADMIN")
                //连接方法
                .and()
                //注册用户
                .withUser("root")
                // 设置密码
                .password(passwordEncoder.encode("abd"))
                .roles("USER");
        
    }
}
