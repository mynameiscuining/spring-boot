package cn.njyazheng.springboot.usepackage;

import cn.njyazheng.springboot.domain.User;
import cn.njyazheng.springboot.domain.UserInfo;
import cn.njyazheng.springboot.usepackage.db01.User01Mapper;
import cn.njyazheng.springboot.usepackage.db02.UserInfo01Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("package")
public class UserService {
    @Autowired
    private UserInfo01Mapper userInfoMapper;

    @Autowired
    private User01Mapper userMapper;

    /**
     * 1.在非配置分布式事务务下不使用注解@Transactional,正常
     * 2.在非配置分布式事务务下使用注解@Transactional数据源全变成db02
     */
    @Transactional
    public void add() {
        User user = new User();
        user.setName("lucy");
        user.setAge(1);
        userMapper.insert(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setAddr("Chinese");
        userInfoMapper.insert(userInfo);
    }

}
