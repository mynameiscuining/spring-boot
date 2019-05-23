package cn.njyazheng.springboot.aop.service;

import cn.njyazheng.springboot.aop.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    public void printUser(User user) {
        user = Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("user is null"));
        System.out.println(user);
    }
}
