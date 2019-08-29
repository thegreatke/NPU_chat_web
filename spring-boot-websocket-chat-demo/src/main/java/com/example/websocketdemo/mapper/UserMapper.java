package com.example.websocketdemo.mapper;

import com.example.websocketdemo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: TheGreatKe
 * @Date: 2018/6/4 15:52
 * Describe: user表SQL语句
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where phone=#{phone}")
    User findUserByPhone(@Param("phone") String phone);

    @Select("select username from user where id=#{id}")  //find name
    String findUsernameById(int id);

    @Insert("insert into user(phone,username,password,gender,avatarImgUrl) values(#{phone},#{username},#{password},#{gender},#{avatarImgUrl})")
    void insert(User user);

    @Select("select id from user where username=#{username}")   //find id
    int findIdByUsername(String username);


    @Select("select * from user where username=#{username}")
    User findUsernameByUsername(@Param("username") String username);


    @Select("select password from user where username=#{username}")
    String findUserPasswordByUsername(@Param("username") String username);

}
