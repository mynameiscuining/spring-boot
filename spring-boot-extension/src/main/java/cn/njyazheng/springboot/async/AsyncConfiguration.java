package cn.njyazheng.springboot.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync//开启异步
public class AsyncConfiguration implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor=new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolExecutor.setCorePoolSize(10);
        //最大线程数
        threadPoolExecutor.setMaxPoolSize(30);
        //队列最大线程数
        threadPoolExecutor.setQueueCapacity(2000);
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }
}
