package com.example.websocketdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.websocketdemo.model.MoodMessage;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class Mood_service {


/**
 * @author: TheGreatKe
 * @Date: 2018/7/15 14:01
 * Describe:留言业务操作
 */
@Service
public interface MoodMessageService {

    /**
     * 保存留言信息
     * @param MoodMessageContent 留言内容
     * @param pageName 留言页
     * @param answerer 留言者
     */
    @Transactional
    void publishMoodMessage(String MoodMessageContent, String pageName, String answerer);

    /**
     * 保存留言回复信息
     * @param MoodMessage
     */
    @Transactional
    MoodMessage publishMoodMessageReply(MoodMessage MoodMessage, String respondent);

    /**
     * 返回最新的留言回复
     * @param MoodMessage
     * @return
     */
    JSONObject MoodMessageNewReply(MoodMessage MoodMessage, String answerer, String respondent);

    /**
     * 获得当前页的所有留言
     * @param pageName 当前页的名称
     * @param pId
     * @return
     */
    JSONObject findAllMoodMessage(String pageName, int pId, String username);

    /**
     * 更新点赞数
     * @return 点赞数
     */
    int updateLikeByPageNameAndId(String pageName, int id);

    /**
     * 分页获得用户所有留言
     */
    JSONObject getUserMoodMessage(int rows, int pageNum, String username);

    /**
     * 返回最新5条留言
     */
    JSONObject findFiveNewComment(int rows, int pageNum);

    /**
     * 获得留言总数
     */
    int countMoodMessageNum();

}}
