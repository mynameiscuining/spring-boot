package cn.njyazheng.springboot.usecustom;

import cn.njyazheng.springboot.domain.User;
import cn.njyazheng.springboot.domain.UserInfo;
import cn.njyazheng.springboot.mapper.UserInfoMapper;
import cn.njyazheng.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("custom")
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    /***
     *
     * @param user
     */
    @Router("db01")
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Router("db02")
    public void addUserInfo(UserInfo user) {
        userInfoMapper.insert(user);
    }

    /**
     * 非分布式事务 注解式对方法的调用不可出现在同一类中(父类,子类).被调用方法注解失效,遵从父类数据源,遵从父类数据源
     * 非注解@Transactional,分布式事务注解式对方法的调用不可出现在同一类中(父类,子类).被调用方法注解失效,数据源丢失
     * 注解@Transactional,分布式事务注解式对方法的调用不可出现在同一类中(父类,子类).被调用方法注解失效,数据源丢失
     */
    @Transactional
    public void add() {
        User user = new User();
        user.setName("lucy");
        user.setAge(1);
        addUser(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setAddr("Chinese");
        addUserInfo(userInfo);
    }

}
