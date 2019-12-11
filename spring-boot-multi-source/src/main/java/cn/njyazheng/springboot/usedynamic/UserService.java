package cn.njyazheng.springboot.usedynamic;

import cn.njyazheng.springboot.domain.User;
import cn.njyazheng.springboot.mapper.UserMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("db01")
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    //注解方法优先
    @DS("db01")
    public boolean addUserDB01(User user){
       userMapper.insert(user);
       return true;
    }
    
    @DS("db02")
    public boolean addUserDB02(User user){
        userMapper.insert(user);
        return true;
    }
}
