package cn.njyazheng.springboot.aop.proxy.interceptor;

import java.lang.reflect.InvocationTargetException;

public class MethodInterceptor implements Interceptor {
    @Override
    public boolean before() {
        System.out.println("-------------------berfore");
        return true;
    }
    
    @Override
    public void after() {
        System.out.println("-------------------after");
    }
    
    @Override
    public Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("-------------------around");
        return invocation.proceed();
    }
    
    @Override
    public void afterReturning() {
        System.out.println("-------------------afterReturning");
    }
    
    @Override
    public void afterThrowing() {
        System.out.println("-------------------afterThrowing");
    }
    
    @Override
    public boolean useAround() {
        return true;
    }
}
