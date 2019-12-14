package cn.njyazheng.springboot.usecustom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
@Aspect
@Slf4j
@Profile("custom")
public class RouterAspect {
    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(cn.njyazheng.springboot.usecustom.Router)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws JsonProcessingException {
        //获取方法名称
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        //获取注解
        Router router = targetMethod.getAnnotation(Router.class);
        //获取注解参数
        String value = router.value();
        DataSourceHolder.setDatasourceKey(value);
        //获取方法入参
        Object[] args = joinPoint.getArgs();
        //参数遍历
        if (args != null || args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                log.info("@Router方法:{} 数据源:{} 参数:{}", targetMethod.getName(), value,objectMapper.writeValueAsString(args[i]));
            }
        }
    }

    @After("pointCut()")
    public void after() {
        DataSourceHolder.clearDatasourceKey();
    }
}
