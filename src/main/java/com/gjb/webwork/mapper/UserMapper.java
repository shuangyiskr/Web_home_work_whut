package com.gjb.webwork.mapper;

import com.gjb.webwork.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO spring_work.users (username, password, email, birthday, avatar,money) " +
            "VALUES (#{username}, #{password}, #{email}, #{birthday}, #{avatar},#{money})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);

    @Select("SELECT * FROM spring_work.users WHERE username = #{username}")
    User getUserByUsername(String username);

    @Update("UPDATE spring_work.users SET password = #{newPassword} WHERE username = #{username} AND password = #{oldPassword}")
    int updatePassword(String username, String oldPassword, String newPassword);

}

