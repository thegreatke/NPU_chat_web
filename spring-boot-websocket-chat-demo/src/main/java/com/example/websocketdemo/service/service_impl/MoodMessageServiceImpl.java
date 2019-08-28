package com.example.websocketdemo.service.service_impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.websocketdemo.utils.JavaScriptCheck;
import com.example.websocketdemo.mapper.MoodMessageMapper;
import com.example.websocketdemo.model.MoodMessage;
import com.example.websocketdemo.service.MoodMessageLikesRecordService;
import com.example.websocketdemo.service.MoodMessageService;
import com.example.websocketdemo.service.UserService;
import com.example.websocketdemo.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author: TheGreatKe
 * @Date: 2018/7/15 14:01
 * Describe:
 */
@Service
public class MoodMessageServiceImpl implements MoodMessageService {

    @Autowired
    MoodMessageMapper moodMessageMapper;
    @Autowired
    MoodMessageLikesRecordService moodMessageLikesRecordService;
    @Autowired
    UserService userService;


    //发布心情留言功能
    @Override
    public void publishMoodMessage(String MoodMessageContent, String pageName, String answerer) {

        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        MoodMessageContent = JavaScriptCheck.javaScriptCheck(MoodMessageContent);//添加了前后<script>标签
        MoodMessage MoodMessage = new MoodMessage(pageName, userService.findIdByUsername(answerer), userService.findIdByUsername(SiteOwner.SITE_OWNER), nowStr, MoodMessageContent);

        moodMessageMapper.publishMoodMessage(MoodMessage);

    }

    @Override
    public MoodMessage publishMoodMessageReply(MoodMessage MoodMessage, String respondent) {
        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        MoodMessage.setMoodMessageDate(nowStr);
        String commentContent = MoodMessage.getMoodMessageContent();
        if('@' == commentContent.charAt(0)){
            MoodMessage.setMoodMessageContent(commentContent.substring(respondent.length() + 1));
        }
        MoodMessage.setRespondentId(userService.findIdByUsername(respondent));
        moodMessageMapper.publishMoodMessage(MoodMessage);
        return MoodMessage;
    }

    @Override
    public JSONObject MoodMessageNewReply(MoodMessage MoodMessage, String answerer, String respondent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",200);
        JSONObject result = new JSONObject();
        result.put("answerer",answerer);
        result.put("respondent",respondent);
        result.put("MoodMessageContent",MoodMessage.getMoodMessageContent());
        result.put("MoodMessageDate",MoodMessage.getMoodMessageDate());
        jsonObject.put("result",result);
        return jsonObject;
    }

    @Override
    public JSONObject findAllMoodMessage(String pageName, int pId, String username) {

        List<MoodMessage> MoodMessages = moodMessageMapper.findAllMoodMessage(pageName, pId);
        JSONObject returnJson,replyJson;
        JSONObject MoodMessageJson = new JSONObject();
        JSONArray replyJsonArray;
        JSONArray MoodMessageJsonArray = new JSONArray();
        List<MoodMessage> MoodMessageReplies;

        returnJson = new JSONObject();
        returnJson.put("status",200);

        for(MoodMessage MoodMessage : MoodMessages){
            MoodMessageJson = new JSONObject();
            MoodMessageJson.put("id",MoodMessage.getId());
            MoodMessageJson.put("answerer",userService.findUsernameById(MoodMessage.getAnswererId()));
            MoodMessageJson.put("MoodMessageDate",MoodMessage.getMoodMessageDate());
            MoodMessageJson.put("likes",MoodMessage.getLikes());
            MoodMessageJson.put("avatarImgUrl",userService.getHeadPortraitUrlByUserId(MoodMessage.getAnswererId()));
            MoodMessageJson.put("MoodMessageContent",MoodMessage.getMoodMessageContent());
            if(null == username){
                MoodMessageJson.put("isLiked",0);
            } else {
                if(!moodMessageLikesRecordService.isLiked(pageName, MoodMessage.getId(), userService.findIdByUsername(username))){
                    MoodMessageJson.put("isLiked",0);
                } else {
                    MoodMessageJson.put("isLiked",1);
                }
            }

            MoodMessageReplies = moodMessageMapper.findMoodMessageReplyByPageNameAndPid(pageName, MoodMessage.getId());
            replyJsonArray = new JSONArray();
            for(MoodMessage reply : MoodMessageReplies){
                replyJson = new JSONObject();
                replyJson.put("answerer", userService.findUsernameById(reply.getAnswererId()));
                replyJson.put("respondent", userService.findUsernameById(reply.getRespondentId()));
                replyJson.put("MoodMessageDate", reply.getMoodMessageDate());
                replyJson.put("MoodMessageContent", reply.getMoodMessageContent());

                replyJsonArray.add(replyJson);
            }
            MoodMessageJson.put("replies",replyJsonArray);
            MoodMessageJsonArray.add(MoodMessageJson);
        }
        returnJson.put("result",MoodMessageJsonArray);

        return returnJson;
    }

    @Override
    public int updateLikeByPageNameAndId(String pageName, int id) {
        moodMessageMapper.updateLikeByPageNameAndId(pageName, id);
        return moodMessageMapper.findLikesByPageNameAndId(pageName, id);
    }

    @Override
    public JSONObject getUserMoodMessage(int rows, int pageNum, String username) {

        int answererId = userService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<MoodMessage> MoodMessages = moodMessageMapper.getUserMoodMessage(answererId);
        PageInfo<MoodMessage> pageInfo = new PageInfo<>(MoodMessages);
        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        JSONObject MoodMessageJson;
        JSONArray MoodMessageJsonArray = new JSONArray();
        for(MoodMessage MoodMessage : MoodMessages){
            MoodMessageJson = new JSONObject();
            MoodMessageJson.put("pageName",MoodMessage.getPageName());
            MoodMessageJson.put("answerer",username);
            MoodMessageJson.put("MoodMessageDate",MoodMessage.getMoodMessageDate());
            if(MoodMessage.getPId() == 0){
                MoodMessageJson.put("MoodMessageContent",MoodMessage.getMoodMessageContent());
                MoodMessageJson.put("replyNum",moodMessageMapper.countReplyNumById(MoodMessage.getId()));
            } else {
                MoodMessageJson.put("MoodMessageContent","@" + userService.findUsernameById(MoodMessage.getRespondentId()) + " " + MoodMessage.getMoodMessageContent());
                MoodMessageJson.put("replyNum",moodMessageMapper.countReplyNumByIdAndRespondentId(MoodMessage.getId(), MoodMessage.getRespondentId()));
            }
            MoodMessageJsonArray.add(MoodMessageJson);
        }

        returnJson.put("result",MoodMessageJsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);
        return returnJson;
    }

    @Override
    public JSONObject findFiveNewComment(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<MoodMessage> fiveLeaveWords = moodMessageMapper.findFiveNewLeaveWord();
        PageInfo<MoodMessage> pageInfo = new PageInfo<>(fiveLeaveWords);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for(MoodMessage MoodMessage : fiveLeaveWords){
            jsonObject = new JSONObject();
            if(MoodMessage.getPId() != 0){
                MoodMessage.setMoodMessageContent("@" + userService.findUsernameById(MoodMessage.getRespondentId()) + " " + MoodMessage.getMoodMessageContent());
            }
            jsonObject.put("pagePath",MoodMessage.getPageName());
            jsonObject.put("answerer",userService.findUsernameById(MoodMessage.getAnswererId()));
            jsonObject.put("leaveWordDate",MoodMessage.getMoodMessageDate().substring(0,10));
            jsonObject.put("leaveWordContent",MoodMessage.getMoodMessageContent());
            jsonArray.add(jsonObject);
        }

        returnJson.put("status",200);
        returnJson.put("result",jsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());
        returnJson.put("pageInfo",pageJson);
        return returnJson;
    }

    @Override
    public int countMoodMessageNum() {
        return moodMessageMapper.countMoodMessageNum();
    }
}
