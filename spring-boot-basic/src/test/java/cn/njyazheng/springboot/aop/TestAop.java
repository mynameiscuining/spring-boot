package cn.njyazheng.springboot.aop;

import cn.njyazheng.springboot.aop.proxy.interceptor.MethodInterceptor;
import cn.njyazheng.springboot.aop.proxy.interceptor.ProxyBean;
import cn.njyazheng.springboot.aop.proxy.service.HelloService;
import cn.njyazheng.springboot.aop.proxy.service.HelloServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAop {
    @Test
    public void testproxy(){
        HelloService helloService=new HelloServiceImpl();
        HelloService proxy= (HelloService)ProxyBean.getProxyBean(helloService,new MethodInterceptor());
        proxy.sayHello("cuining");
    }
}
