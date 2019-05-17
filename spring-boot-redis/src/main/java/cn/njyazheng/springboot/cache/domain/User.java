package cn.njyazheng.springboot.cache.domain;

public class User {
    private Integer id;
    private String userName;
    private String pwd;
    private Integer availabe;
    private String node;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public Integer getAvailabe() {
        return availabe;
    }
    
    public void setAvailabe(Integer availabe) {
        this.availabe = availabe;
    }
    
    public String getNode() {
        return node;
    }
    
    public void setNode(String node) {
        this.node = node;
    }
}
