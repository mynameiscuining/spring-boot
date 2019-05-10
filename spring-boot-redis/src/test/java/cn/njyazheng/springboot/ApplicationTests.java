package cn.njyazheng.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    //测试连接
    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("test","test");
    }
    //回调,一个连接中执行多条命令
    @Test
    public void testSessionCallback(){
       redisTemplate.execute(new SessionCallback() {
           @Override
           public Object execute(RedisOperations redisOperations) throws DataAccessException {
               redisOperations.opsForValue().set("test2",1);
               return null;
           }
       });
    }
    @Test
    //事务,不支持回滚,而是取消事务
    public void testTransaction(){
        redisTemplate.opsForValue().set("key1","value1");
        List list= (List) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //设置监控key1
                redisOperations.watch("key1");
                //开启事务,在 exec 命令执行前，全部都只是进入队列
                redisOperations.multi();
                //一系列操作
               Object value= redisOperations.opsForValue().get("key1");
                System.out.println("key1-"+value);
                redisOperations.opsForValue().set("key2","value2");
                //执行事务
                return redisOperations.exec();
            }
        });
        //开启事务到执行事务,每条在事务结束获取的结果
        System.out.println(list);
    }
    @Test
    //redis流水线技术,不是一条条发送命令,而是一次性发送命令,性能会得到一定的提高,但有时会遇到网络的传输速度的瓶颈
    //每条命令只是放进队列,并未执行
    public void testPipeline(){
        LocalTime start=LocalTime.now();
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                IntStream.rangeClosed(1,10000).forEach(i->{
                    redisOperations.opsForValue().set("pipeline"+i,"value"+i);
                    System.out.println(redisOperations.opsForValue().get("pipeline"+i));
                });
                return null;
            }
        });
        LocalTime end=LocalTime.now();
        System.out.println("执行耗时:"+ Duration.between(start,end).toMillis());
    }
    
}
