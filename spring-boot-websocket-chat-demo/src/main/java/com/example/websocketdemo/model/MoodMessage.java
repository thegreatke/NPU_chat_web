package com.example.websocketdemo.model;


import lombok.Data;

    @Data
    public class MoodMessage {

        //主键
        private int id;

        /**
         * 心情的版区域，或者标签，分为学习，或者情感，生活等等
         */
        private String pageName;



        /**
         * 留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id
         */
        private int PId=0;




        /**
         * 留言者
         */
        private int answererId;




        /**
         *  被回复者
         */
        private int RespondentId;



        /**
         * 留言日期
         */
        private String MoodMessageDate;

        /**
         * 喜欢点赞的个数
         */
        private int likes=0;

        /**
         * 留言内容
         */
        private String MoodMessageContent;



        //专用构造函数
        public MoodMessage(String pageName, int answererId, int respondentId, String leaveMessageDate, String leaveMessageContent) {
            this.pageName = pageName;
            this.answererId = answererId;
            this.RespondentId = respondentId;
            this.MoodMessageDate = leaveMessageDate;
            this.MoodMessageContent = leaveMessageContent;
        }

        public MoodMessage() {
        }


    }


