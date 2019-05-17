package cn.njyazheng.springboot.database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishMessageController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    
    //redis客户端发布订阅 publish topic-channel mynameiscuining
    @GetMapping("publish")
    public String publish(){
        redisTemplate.convertAndSend("topic-channel","mynameiscuining");
        return  "success";
    }
    
}
