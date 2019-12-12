package cn.njyazheng.springboot.usepackage;

import cn.njyazheng.springboot.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("package")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        userService.add();
    }



}
