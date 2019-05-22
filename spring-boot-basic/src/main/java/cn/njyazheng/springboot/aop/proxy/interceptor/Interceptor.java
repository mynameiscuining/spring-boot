package cn.njyazheng.springboot.aop.proxy.interceptor;

import java.lang.reflect.InvocationTargetException;

public interface Interceptor {
    //事前方法
    boolean before();
    
    //事后方法
    void after();
    
    //环绕方法
    Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException;
    
    //返回方法,没有异常才会执行
    void afterReturning();
    
    //事后异常方法,异常才会执行
    void afterThrowing();
    
    //是否使用around取代原有方法
    boolean useAround();
    
}
