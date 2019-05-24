package cn.njyazheng.springboot.async.controller;

import cn.njyazheng.springboot.async.service.AsyncService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
    private final static Logger logger = LoggerFactory.getLogger(AsyncController.class);
    @Autowired
    private AsyncService asyncService;
    
    @GetMapping("/async")
    public String async() {
        String transactional = RandomStringUtils.randomAlphanumeric(32);
        logger.info("-------------------------------transactional:{},currentThread:{}", transactional, Thread.currentThread().getName());
        asyncService.async(transactional);
        return transactional;
    }
    
}
