package cn.njyazheng.springboot.usedynamic;

import cn.njyazheng.springboot.domain.User;
import cn.njyazheng.springboot.domain.UserInfo;
import cn.njyazheng.springboot.mapper.UserMapper;
//import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("dynamic")
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoService userInfoService;

    //注解方法优先
    //@DS("db01")
    public boolean addUserDB01(User user) {
        userMapper.insert(user);
        return true;
    }

   // @DS("db02")
    public boolean addUserDB02(User user) {
        userMapper.insert(user);
        return true;
    }

    //@DS("db02")
    public User selectUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    /**
     * 同一个数据源会回滚
     */
    @Transactional
    public void insertSingleSource() {
        User user = new User();
        user.setId(2);
        user.setAge(1);
        user.setName("lily");
        addUserDB01(user);
        selectUser();
        user = new User();
        user.setId(1);
        user.setName("lucy");
        addUserDB01(user);

    }

    /**
     * 1.在注解下Transactional同类子方法多个数据源会失效,数据源为同一个,此数据源primary配置项
     * 2.不注解Transactional,可以查到多个数据源正常
     */
    @Transactional
    public void insertDiffSource() {
        User user = null;
        selectUser();
        user = new User();
        user.setName("lucy");
        user.setAge(1);
        addUserDB02(user);
        user = new User();
        user.setName("lilei");
        user.setAge(1);
        addUserDB02(user);
        user = new User();
        user.setName("lily");
        user.setId(22);
        user.setAge(1);
        addUserDB01(user);
    }

    /**
     * 1.在注解@Transactional下,不同类子方法调用@DS依然失效,数据源为父类方法定义的数据源
     * 2.不注解Transactional,可以查到多个数据源正常
     */
    @Transactional
    public void insertDifffCalssDiffSource() {
        User user = new User();
        user.setName("lucy");
        user.setAge(1);
        addUserDB01(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setAddr("Chinese");
        userInfoService.add(userInfo);

    }
}
