package cn.njyazheng.springboot.aop.aspect;

import cn.njyazheng.springboot.aop.domain.User;

public class UserIntroduceImpl implements UserIntroduce {
    @Override
    public boolean isNull(User user) {
        return user==null;
    }
}
