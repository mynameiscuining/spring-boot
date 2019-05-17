package cn.njyazheng.springboot.cache.service;

import cn.njyazheng.springboot.cache.domain.User;
import cn.njyazheng.springboot.cache.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    //表示将结果放进缓存
    @CachePut(value = "userCache",key = "'user_'+#result.id")
    public User addUser(User user) {
        userMapper.addUser(user);
        return user;
    }
    //移除缓存
    @CacheEvict(value = "userCache",key = "'user_'+#id")
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }
    //如果结果不为空才进行缓存
    @CachePut(value = "userCache",condition = "#result!='null'",key = "'user_'+#result.id")
    public User modifyUser(User user) {
        //内部调用方法,将不会启用getUser的缓存机制,解决这个问题:分成两个service,进行调用
        User temUser= getUser(user.getId());
        if(temUser==null){
            return  null;
        }
        BeanUtils.copyProperties(user,temUser);
        userMapper.modifyUser(temUser);
        return temUser;
    }
    //从缓存获取,没有则查询数据库,缓存数据,
    @Cacheable(value = "userCache",key ="'user_'+#id" )
    public User getUser(Integer id) {
        return  userMapper.getUser(id);
    }
    //命中率低的不做缓存
    public List<User> getUsers(User user) {
        return  userMapper.getUsers(user);
    }
}
