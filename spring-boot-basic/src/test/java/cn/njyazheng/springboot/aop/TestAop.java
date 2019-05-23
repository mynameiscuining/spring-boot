package cn.njyazheng.springboot.aop;

import cn.njyazheng.springboot.aop.aspect.UserIntroduce;
import cn.njyazheng.springboot.aop.domain.User;
import cn.njyazheng.springboot.aop.proxy.interceptor.MethodInterceptor;
import cn.njyazheng.springboot.aop.proxy.interceptor.ProxyBean;
import cn.njyazheng.springboot.aop.proxy.service.HelloService;
import cn.njyazheng.springboot.aop.proxy.service.HelloServiceImpl;
import cn.njyazheng.springboot.aop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAop {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void testproxy(){
        HelloService helloService=new HelloServiceImpl();
        HelloService proxy= (HelloService)ProxyBean.getProxyBean(helloService,new MethodInterceptor());
        proxy.sayHello("cuining");
    }
    
    @Test
    public void aopwithException(){
        User user=null;
        userService.printUser(user);
    }
    @Test
    public void aopwithOk(){
        User user=new User();
        user.setAvailabe(1);
        user.setId(1);
        user.setNode("1");
        user.setPwd("1");
        user.setUserName("1");
        userService.printUser(user);
    }
    @Test
    public void testUserIntroduce(){
        User user=null;
        UserIntroduce userIntroduce=(UserIntroduce)userService;
        if(userIntroduce.isNull(user)){
            System.out.println("用户为空不打印用");
        }else {
            userService.printUser(user);
        }
        
    }
    
}
