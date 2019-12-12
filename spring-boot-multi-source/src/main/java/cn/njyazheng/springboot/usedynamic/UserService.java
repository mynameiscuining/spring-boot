package cn.njyazheng.springboot.usedynamic;

import cn.njyazheng.springboot.domain.User;
import cn.njyazheng.springboot.mapper.UserMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@DS("db01")
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //注解方法优先
    @DS("db01")
    public boolean addUserDB01(User user) {
        userMapper.insert(user);
        return true;
    }

    @DS("db02")
    public boolean addUserDB02(User user) {
        userMapper.insert(user);
        return true;
    }

    @DS("db02")
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
     * 子方法@Ds会失效,数据源为同一个
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
}
