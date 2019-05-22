package cn.njyazheng.springboot.aop.proxy.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBean implements InvocationHandler {
   private Object target;
   private Interceptor interceptor;
    
    public static Object getProxyBean(Object target,Interceptor interceptor){
        ProxyBean proxyBean=new ProxyBean();
        proxyBean.target=target;
        proxyBean.interceptor=interceptor;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),proxyBean);
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object retunvalue=null;
        try {
            Invocation invocation= new Invocation(args,method,target);
            if(interceptor.before()){
                retunvalue=interceptor.around(invocation);
            }else {
                retunvalue =invocation.proceed();
            }
            interceptor.afterReturning();
        }catch (Exception e){
            interceptor.afterThrowing();
            e.printStackTrace();
        }finally {
            interceptor.after();
        }
        return retunvalue;
    }
}
