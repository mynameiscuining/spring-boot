package cn.njyazheng.springboot.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);
    @Async//声明异步调用
    public void async(String transactional) {
        LOGGER.info("-------------------------------transactional:{},currentThread:{}",transactional,Thread.currentThread().getName());
    }
}
