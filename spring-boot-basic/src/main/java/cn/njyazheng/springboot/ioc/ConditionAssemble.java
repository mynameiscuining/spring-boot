package cn.njyazheng.springboot.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
//条件
@Conditional(value = ConditionImpl.class)
public class ConditionAssemble {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionAssemble.class);
    @PostConstruct
    public void init(){
        LOGGER.info("没有日志信息,没有被注入容器");
    }
}
