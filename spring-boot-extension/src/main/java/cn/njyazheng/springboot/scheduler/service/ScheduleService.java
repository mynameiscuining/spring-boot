package cn.njyazheng.springboot.scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    //fixedDelay (ms)
    //fixedRate(ms)
    @Scheduled(fixedDelay = 10000 )
    public void  per(){
        LOGGER.info("-------------------从启动开始每10秒执行一次");
    }
    @Scheduled(initialDelay = 1000,fixedDelay = 10000)
    public void  delay(){
        LOGGER.info("-------------------从启动开始先延迟10秒,之后,每10秒执行一次");
    }
}
