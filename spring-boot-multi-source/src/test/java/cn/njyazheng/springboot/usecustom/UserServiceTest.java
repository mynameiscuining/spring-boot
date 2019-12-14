package cn.njyazheng.springboot.usecustom;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("custom")
public class UserServiceTest {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        businessService.add();
    }

    @Test
    public void insert2() {
        userService.add();
    }


}
