package cn.njyazheng.springboot.controller;

import cn.njyazheng.springboot.config.PropertiesFromConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private PropertiesFromConfig properties;
    //value使用SPEL表达式
    @Value("#{propertiesFromConfig.getMap()}")
    private Map map;
    
    
    @GetMapping("/config/properties")
    public PropertiesFromConfig config(){
        return properties;
    }
    
    @GetMapping("/value/el")
    public Map valueel(){
        return map;
    }
}
