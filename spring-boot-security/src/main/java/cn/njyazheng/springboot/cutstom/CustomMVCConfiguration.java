package cn.njyazheng.springboot.cutstom;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomMVCConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //'/custom/login/page'------->'/custom/login.html'
        //登录页面
        registry.addViewController("/custom/login/page").setViewName("login");
        registry.addViewController("/custom/logout").setStatusCode(HttpStatus.OK);
        //成功跳转的页面
        registry.addViewController("/custom/admin/welcome").setViewName("admin/welcome");
    }
}
