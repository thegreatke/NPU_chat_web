package com.example.websocketdemo.model;


import lombok.Data;

    @Data
    public class MoodMessage {

        private int mood_id;

        /**
         * 心情页
         */
        private String pageName;

        /**
         * 留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id
         */
        private int parent_id=0;

        /**
         * 留言者
         */
        private int answerer_Id;

        /**
         * 被回复者
         */
        private int respondent_Id;

        /**
         * 留言日期
         */
        private String leave_MessageDate;

        /**
         * 喜欢数
         */
        private int likes=0;

        /**
         * 留言内容
         */
        private String MoodMessageContent;

        public MoodMessage() {
        }

        public MoodMessage(int mood_id, String pageName, int parent_id, int answerer_Id, int respondent_Id, String leave_MessageDate, int likes, String MoodMessageContent) {
            this.mood_id = mood_id;
            this.pageName = pageName;
            this.parent_id = parent_id;
            this.answerer_Id = answerer_Id;
            this.respondent_Id = respondent_Id;
            this.leave_MessageDate = leave_MessageDate;
            this.likes = likes;
            this.MoodMessageContent = MoodMessageContent;
        }
    }


