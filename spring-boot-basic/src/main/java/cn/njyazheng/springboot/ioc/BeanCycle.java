package cn.njyazheng.springboot.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanCycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanCycle.class);
    @PostConstruct
    public void init(){
        LOGGER.info("--------------------@PostConstruct定义初始化方法");
    }
    @PreDestroy
    public void destroy(){
        LOGGER.info("--------------------@PreDestroy定义bean的销毁方法");
    }
}
