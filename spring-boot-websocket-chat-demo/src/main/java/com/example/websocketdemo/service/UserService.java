package com.example.websocketdemo.service;

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





}
