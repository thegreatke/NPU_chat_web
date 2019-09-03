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
         * phone
         */
        private String phone;


        public User(int id, String username, String password, String gender) {
                this.id = id;
                this.username = username;
                this.password = password;
                this.gender = gender;
        }

        /**
         * 个人简介
         */
        private String personalBrief;


        public User(int id, String username, String password, String gender, String phone, String personalBrief, String avatarImgUrl) {
                this.id = id;
                this.username = username;
                this.password = password;
                this.gender = gender;
                this.phone = phone;
                this.personalBrief = personalBrief;
                this.avatarImgUrl = avatarImgUrl;
        }

        /**
         * 头像地址
         */
        private String avatarImgUrl;

        public User(String username, String password) {
                this.username = username;
                this.password = password;
        }
}
