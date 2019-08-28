package com.example.websocketdemo.service.service_impl;

import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.websocketdemo.mapper.UserMapper;
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;



    @Override
    public String findUsernameById(int id) {
        return userMapper.findUsernameById(id);
    }


    @Override
    public int findIdByUsername(String username) {
        return userMapper.findIdByUsername(username);
    }


}
