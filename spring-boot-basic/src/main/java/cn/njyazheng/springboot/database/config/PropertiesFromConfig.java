package cn.njyazheng.springboot.database.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Component
//属性要有get,set方法
@ConfigurationProperties(value = "pdc")
public class PropertiesFromConfig implements Serializable {
    private String name;
    
    private List<Integer> list;
    
    private Map<Integer,String>map;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Integer> getList() {
        return list;
    }
    
    public void setList(List<Integer> list) {
        this.list = list;
    }
    
    public Map<Integer, String> getMap() {
        return map;
    }
    
    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }
}
