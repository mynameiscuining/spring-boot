package cn.njyazheng.springboot.aop.aspect;

import cn.njyazheng.springboot.aop.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
/**
 * execution(* com.xyz.service..*.*(..))---->The execution of any method defined in the service package or one of its sub-packages
 * @annotation(com.xgj.aop.spring.advisor.aspectJ.function.NeedTest)切入注解@NeedTest
 *
 */
@Aspect
public class UserAspect {
    
    //引入
    @DeclareParents(value = "cn.njyazheng.springboot.aop.service.UserService",
            defaultImpl = cn.njyazheng.springboot.aop.aspect.UserIntroduceImpl .class)
    private UserIntroduce userIntroduce;
    
    
    /**
     * 1.第一个* 表示返回值
     * 2.(..)表示方法的任意参数
     * bean name or id=符合*Service
     */
    @Pointcut("execution(* cn.njyazheng.springboot.aop.service.UserService.printUser(..))&& bean(*Service) ")
    public void pointcut() {
    }
    //指定参数user,穿进去,顺序JoinPoint joinPoint,在前
    @Before("pointcut()&& args(user)")
    public void before( JoinPoint joinPoint,User user) {
        System.out.println(joinPoint.getArgs());
        System.out.println("===================before"+user);
    }
    
    @After("pointcut()&&args(user)")
    public void after(JoinPoint joinPoint,User user) {
        
        System.out.println("===================after"+user);
    }
    
    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.out.println("===================afterReturning");
    }
    
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("===================afterThrowing");
    }
    
    @Around("pointcut()&&args(user)")
    public void around(ProceedingJoinPoint joinPoint,User user) throws Throwable {
        System.out.println("===================around before"+joinPoint.getArgs());
        joinPoint.proceed();
        System.out.println("===================around after"+user);
    }
}
