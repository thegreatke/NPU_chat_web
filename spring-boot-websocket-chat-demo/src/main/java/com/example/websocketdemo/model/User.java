package com.example.websocketdemo.model;

import lombok.Data;

import java.util.List;
@Data
public class User {

        private int id;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * 性别
         */
        private String gender;


        /**
         * 个人简介
         */
        private String personalBrief;


        /**
         * 头像地址
         */
        private String avatarImgUrl;

}
