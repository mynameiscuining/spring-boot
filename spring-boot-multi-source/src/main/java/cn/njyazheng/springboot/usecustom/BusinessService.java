package cn.njyazheng.springboot.usecustom;

import cn.njyazheng.springboot.domain.User;
import cn.njyazheng.springboot.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("custom")
public class BusinessService {
    @Autowired
    private UserService userService;

   @Transactional
    public void add() {
        User user = new User();
        user.setName("lucy");
        user.setAge(1);
        userService.addUser(user);

        UserInfo userInfo = new UserInfo();
        userInfo.setAddr("Chinese");
        userService.addUserInfo(userInfo);
    }

}
