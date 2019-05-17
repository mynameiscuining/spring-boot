package cn.njyazheng.springboot.cache.mapper;

import cn.njyazheng.springboot.cache.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert(" insert into t_user (user_name,pwd,availabe,note) value(#{userName},#{pwd},#{availabe},#{note}) ")
    int addUser(User user);
    @Delete(" delete from t_user where id =#{id} ")
    int deleteUser(Integer id);
    @Update(" update from t_user set user_name=#{userName},pwd=#{pwd},availabe=#{availabe},note=#{note} where id =#{id} ")
    int modifyUser(User user);
    @Select("select *  from t_user where id =#{id}")
    User getUser(Integer id);
    @Select("<script>" +
            "select * from t_user where 1=1" +
            " <if test='id!=null'> and  id=#{id} </if>" +
            " <if test='userName!=null'> and  user_name=#{userName} </if>" +
            " <if test='availabe!=null'> and  availabe=#{availabe} </if>" +
            " <if test='note!=null'> and  note=#{note} </if>" +
            "</script>")
    List<User>getUsers(User user);
    
}
