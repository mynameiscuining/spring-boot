package cn.njyazheng.springboot.mapper;

import cn.njyazheng.springboot.domain.UserInfo;
import org.apache.ibatis.jdbc.SQL;

public class UserInfoSqlProvider {

    public String insertSelective(UserInfo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("userinfo");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getAddr() != null) {
            sql.VALUES("addr", "#{addr,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserInfo record) {
        SQL sql = new SQL();
        sql.UPDATE("userinfo");
        
        if (record.getAddr() != null) {
            sql.SET("addr = #{addr,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}