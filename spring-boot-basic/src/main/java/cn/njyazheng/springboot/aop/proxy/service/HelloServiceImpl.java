package cn.njyazheng.springboot.aop.proxy.service;

import org.springframework.util.StringUtils;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        if(StringUtils.isEmpty(name)){
            throw new RuntimeException("name is empty");
        }
        System.out.println("hello "+name);
    }
}
