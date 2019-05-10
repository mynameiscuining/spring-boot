package cn.njyazheng.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
//消息监听
public class ListenterConfiguration {
   
    //连接工厂
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    //监听器
    @Autowired
    private MessageListener messageListener;
    //任务池
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    
    /**
     * 创建任务池,运行线程等待处理redis消息
     * @return
     */
    @Bean
    public ThreadPoolTaskScheduler initThreadTaskScheduler(){
         if(threadPoolTaskScheduler!=null){
             return threadPoolTaskScheduler;
         }
         threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
         threadPoolTaskScheduler.setPoolSize(20);
         return threadPoolTaskScheduler;
    }
    
    /**
     * 定义redis的监听容器
     * @return
     */
    @Bean
    public RedisMessageListenerContainer initContainer(){
        RedisMessageListenerContainer redisMessageListenerContainer=new RedisMessageListenerContainer();
        //定义容器redis连接工厂
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        //设置线程池
        redisMessageListenerContainer.setTaskExecutor(initThreadTaskScheduler());
        //定义监听渠道
        Topic topic=new ChannelTopic("topic-channel");
        //监听任务
        redisMessageListenerContainer.addMessageListener(messageListener,topic);
        return redisMessageListenerContainer;
    }
}
