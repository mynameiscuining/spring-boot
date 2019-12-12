package cn.njyazheng.springboot.usedynamic;

import cn.njyazheng.springboot.domain.UserInfo;
import cn.njyazheng.springboot.mapper.UserInfoMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DS("db02")
@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @DS("db02")
    public void add(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }
}
