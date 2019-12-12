package cn.njyazheng.springboot.usedynamic;

import cn.njyazheng.springboot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        User user = new User();
        user.setName("tom");
        userService.addUserDB01(user);
        user.setName("jack");
        userService.addUserDB02(user);
    }

    @Test
    public void insertSingleSource() {
         userService.insertSingleSource();
    }

    @Test
    public void insertDiffSource() {
        userService.insertDiffSource();
    }
    @Test
    public void insertDifffCalssDiffSource() {
        userService.insertDifffCalssDiffSource();
    }


}
