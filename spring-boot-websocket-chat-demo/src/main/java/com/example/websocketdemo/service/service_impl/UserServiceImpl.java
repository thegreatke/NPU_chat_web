package com.example.websocketdemo.service.service_impl;

import com.example.websocketdemo.model.User;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.websocketdemo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
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


    @Override
    public int login(String userName,String password){

//            userMapper.findUserPasswordByUsername(userName)
        User user;
        int result = 0;
        user = userMapper.findUsernameByUsername(userName);
        if(user == null){

            result = 0;
        }else if(user.getPassword().equals(password)){

            result = 1;
        }else {
            result = 2;

        }

        return result;
    }

    @Override
    public int regist(String userName,String password, String gender){

//            userMapper.findUserPasswordByUsername(userName)

        User user_test;
        int result = 0;
        user_test = userMapper.findUsernameByUsername(userName);
        if(user_test != null){

            result = 0;
        }
        else{
            User user = new User(userName, password);
            user.setGender(gender);
            userMapper.insert(user);
            result = 1;
        }

        return result;
    }

}
