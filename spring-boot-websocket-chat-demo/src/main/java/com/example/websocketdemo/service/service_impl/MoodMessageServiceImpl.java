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
import com.example.websocketdemo.utils.SiteOwner;
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


    //发布某一条心情留言,保存功能
    @Override
    public void publishMoodMessage(String MoodMessageContent,String title, String pageName, String answerer) {

        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        MoodMessageContent = JavaScriptCheck.javaScriptCheck(MoodMessageContent);//添加了前后<script>标签
        MoodMessage moodMessage = new MoodMessage(pageName, userService.findIdByUsername(answerer), userService.findIdByUsername(SiteOwner.SITE_OWNER), nowStr, MoodMessageContent, title);

        moodMessageMapper.publishMoodMessage(moodMessage);

    }

    //保存对某个留言的回复。
    @Override
    public MoodMessage publishMoodMessageReply(MoodMessage moodMessage, int pid) {
        TimeUtil timeUtil = new TimeUtil();
        String nowStr = timeUtil.getFormatDateForFive();
        moodMessage.setMoodMessageDate(nowStr);
        String commentContent = moodMessage.getMoodMessageContent();
        int id_local = moodMessageMapper.findAnswererIdByPid(pid);
        String respondent = userService.findUsernameById(id_local);

        if('@' == commentContent.charAt(0)){
            moodMessage.setMoodMessageContent(commentContent.substring(respondent.length() + 1));  //截取字符串的长度，决定起始位置
        }
        moodMessage.setRespondentId(userService.findIdByUsername(respondent));
        moodMessage.setTitle(null);
        moodMessageMapper.publishMoodMessage(moodMessage);
        return moodMessage;
    }

    //最新留言信息
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
    //返回所有留言信息
    @Override
    public JSONObject findAllMoodMessage(String pageName, int pId, String username) {

        List<MoodMessage> moodMessages = moodMessageMapper.findAllMoodMessage(pageName, pId);
        JSONObject returnJson,replyJson;
        JSONObject MoodMessageJson = new JSONObject();
        JSONArray replyJsonArray;
        JSONArray MoodMessageJsonArray = new JSONArray();
        List<MoodMessage> MoodMessageReplies;

        returnJson = new JSONObject();
        returnJson.put("status",200);

        for(MoodMessage moodMessage : moodMessages){
            MoodMessageJson = new JSONObject();
            MoodMessageJson.put("id",moodMessage.getId());
            MoodMessageJson.put("answerer",userService.findUsernameById(moodMessage.getAnswererId()));
            MoodMessageJson.put("MoodMessageDate",moodMessage.getMoodMessageDate());
            MoodMessageJson.put("likes",moodMessage.getLikes());
//            MoodMessageJson.put("avatarImgUrl",userService.getHeadPortraitUrlByUserId(moodMessage.getAnswererId()));   //暂时不考虑头像功能
            MoodMessageJson.put("MoodMessageContent",moodMessage.getMoodMessageContent());
            if(null == username){
                MoodMessageJson.put("isLiked",0);
            } else {
                if(!moodMessageLikesRecordService.isLiked(pageName, moodMessage.getId(), userService.findIdByUsername(username))){
                    MoodMessageJson.put("isLiked",0);
                } else {
                    MoodMessageJson.put("isLiked",1);
                }
            }

            MoodMessageReplies = moodMessageMapper.findMoodMessageReplyByPageNameAndPid(pageName, moodMessage.getId());
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
    //更新点赞数目
    @Override
    public int updateLikeByPageNameAndId(String pageName, int id) {
        moodMessageMapper.updateLikeByPageNameAndId(pageName, id);
        return moodMessageMapper.findLikesByPageNameAndId(pageName, id);
    }
    //分页获得某一个用户所有留言
    @Override
    public JSONObject getUserMoodMessage(int rows, int pageNum, String username) {

        int answererId = userService.findIdByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<MoodMessage> moodMessages = moodMessageMapper.getUserMoodMessage(answererId);
        PageInfo<MoodMessage> pageInfo = new PageInfo<>(moodMessages);
        JSONObject returnJson = new JSONObject();
        returnJson.put("status",200);
        JSONObject MoodMessageJson;
        JSONArray MoodMessageJsonArray = new JSONArray();
        for(MoodMessage moodMessage : moodMessages){
            MoodMessageJson = new JSONObject();
            MoodMessageJson.put("pageName",moodMessage.getPageName());
            MoodMessageJson.put("answerer",username);
            MoodMessageJson.put("MoodMessageDate",moodMessage.getMoodMessageDate());
            if(moodMessage.getPId() == 0){
                MoodMessageJson.put("MoodMessageContent",moodMessage.getMoodMessageContent());
                MoodMessageJson.put("replyNum",moodMessageMapper.countReplyNumById(moodMessage.getId()));
            } else {
                MoodMessageJson.put("MoodMessageContent","@" + userService.findUsernameById(moodMessage.getRespondentId()) + " " + moodMessage.getMoodMessageContent());
                MoodMessageJson.put("replyNum",moodMessageMapper.countReplyNumByIdAndRespondentId(moodMessage.getId(), moodMessage.getRespondentId()));
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

    //rows表示每页返回数据的行数           pageNum表示第几页
    @Override
    public JSONObject findFiveNewComment(int rows, int pageNum) {
        JSONObject returnJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);  //设置页面的位置和展示的数据条目数
        List<MoodMessage> fiveLeaveWords = moodMessageMapper.findFiveNewLeaveWord();
        PageInfo<MoodMessage> pageInfo = new PageInfo<>(fiveLeaveWords);//包装

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for(MoodMessage moodMessage : fiveLeaveWords){
            jsonObject = new JSONObject();
            if(moodMessage.getPId() != 0){     //回复非版主的情况添加@someone的效果
                moodMessage.setMoodMessageContent("@" + userService.findUsernameById(moodMessage.getRespondentId()) + " " + moodMessage.getMoodMessageContent());
            }
            jsonObject.put("id",moodMessage.getId());
            jsonObject.put("pid",moodMessage.getPId());

            jsonObject.put("pageName",moodMessage.getPageName());
            jsonObject.put("title",moodMessage.getTitle());

            jsonObject.put("likes",moodMessage.getLikes());

            jsonObject.put("answerer",userService.findUsernameById(moodMessage.getAnswererId()));
            jsonObject.put("leaveWordDate",moodMessage.getMoodMessageDate());//.substring(0,10)
            jsonObject.put("moodWordContent",moodMessage.getMoodMessageContent());
            jsonArray.add(jsonObject);
        }

        returnJson.put("status",200);
        returnJson.put("result",jsonArray);
//        JSONObject pageJson = new JSONObject();
//        pageJson.put("pageNum",pageInfo.getPageNum());
//        pageJson.put("pageSize",pageInfo.getPageSize());
//        pageJson.put("total",pageInfo.getTotal());
//        pageJson.put("pages",pageInfo.getPages());
//        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
//        pageJson.put("isLastPage",pageInfo.isIsLastPage());
//        returnJson.put("pageInfo",pageJson);
        return returnJson;
    }

    @Override
    public int countMoodMessageNum() {
        return moodMessageMapper.countMoodMessageNum();
    }
}
