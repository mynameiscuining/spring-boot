package cn.njyazheng.springboot.usepackage.db02;

import cn.njyazheng.springboot.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface UserInfo01Mapper {
    @Delete({
            "delete from userinfo",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into userinfo ( addr)",
            "values ( #{addr,jdbcType=VARCHAR})"
    })
    int insert(UserInfo record);

    @InsertProvider(type = UserInfoSqlProvider.class, method = "insertSelective")
    int insertSelective(UserInfo record);

    @Select({
            "select",
            "id, addr",
            "from userinfo",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "addr", property = "addr", jdbcType = JdbcType.VARCHAR)
    })
    UserInfo selectByPrimaryKey(Integer id);

    @UpdateProvider(type = UserInfoSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserInfo record);

    @Update({
            "update userinfo",
            "set addr = #{addr,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserInfo record);
}