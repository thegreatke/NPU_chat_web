package com.example.websocketdemo.service;

import com.example.websocketdemo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {

    /**
     * 通过id查找用户名
     * @param id
     * @return
     */
    String findUsernameById(int id);


    /**
     * 通过用户名查找id
     * @param username
     * @return
     */
    int findIdByUsername(String username);


    /**
     * 通过用户名查找id
     * @param
     * @return
     */



    public int login(String userName,String password);



    public int regist(String userName,String password, String gender);
//    /**
//     * 注册用户
//     * @param user 用户
//     * @return "1"--用户存在，插入失败             "2"--用户不存在，插入成功
//     */
//    @Transactional
//    String insert(User user);




}
