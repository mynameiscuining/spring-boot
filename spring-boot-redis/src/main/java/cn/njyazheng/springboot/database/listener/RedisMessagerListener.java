package cn.njyazheng.springboot.database.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
//发布消息监听
public class RedisMessagerListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessagerListener.class);
    @Override
    public void onMessage(Message message, byte[] bytes) {
        //消息体
        String body=new String( message.getBody());
        //渠道名称
        String topic=new String(bytes);
        LOGGER.info("----------------------------------消息体:"+body);
        LOGGER.info("----------------------------------渠道名称:"+topic);
    }
}
