package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.MoodMessageLikesRecord;
import com.example.websocketdemo.service.MoodMessageLikesRecordService;
import com.example.websocketdemo.service.MoodMessageService;
import com.example.websocketdemo.service.UserService;
import com.example.websocketdemo.model.MoodMessage;

import com.example.websocketdemo.utils.JavaScriptCheck;
import com.example.websocketdemo.utils.TimeUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/")
public class MoodController {


    private Logger logger = LoggerFactory.getLogger(MoodController.class);

    @Autowired
    MoodMessageService moodMessageService;
    @Autowired
    MoodMessageLikesRecordService moodMessageLikesRecordService;
    @Autowired
    UserService userService;


    /**
     * 发表留言
     * @param moodMessageContent 留言内容
     * @param pageName 留言页
     * @Param ("answerer") String answerer 留言者的用户名
     * @return
     */
    @PostMapping("/publishMoodMessage")
    public JSONObject publishMoodMessage(@RequestParam("moodMessageContent") String moodMessageContent,
                                          @RequestParam("pageName") String pageName,
                                          @RequestParam ("answerer") String answerer){
        JSONObject jsonObject;
        if (answerer ==null)
        {logger.info("please input something");
            jsonObject = new JSONObject();
            jsonObject.put("status",403);
            jsonObject.put("result","please input something");
            return jsonObject;
        }

        moodMessageService.publishMoodMessage(moodMessageContent, pageName, answerer);
        return moodMessageService.findAllMoodMessage(pageName, 0, answerer);

    }


    /**
     * 获得当前页并且当前用户的留言
     * @param pageName 当前页
     * @RequestParam ("answerer")  answerer 用户名
     * @return
     */
    @GetMapping("/getPageMoodMessage")
    public JSONObject getPageMoodMessage(@RequestParam("pageName") String pageName,
                                          @RequestParam ("answerer") String answerer){

        return moodMessageService.findAllMoodMessage(pageName, 0, answerer);
    }




    /**
     * 发布对留言中的评论
     * @return
     */
    @PostMapping("/publishMoodMessageReply")
    public JSONObject publishMoodMessageReply(MoodMessage moodMessage,
                                               @RequestParam("parentId") String parentId, //父留言的主键PID
                                                  @RequestParam("respondent") String respondent,//被回复的人的name
                                               @RequestParam ("answerer") String answerer){
        String username = null;
        JSONObject jsonObject;

        moodMessage.setAnswererId(userService.findIdByUsername(answerer));
        moodMessage.setPId(Integer.parseInt(parentId));  //去除了substring的前面的一行缩进
        moodMessage.setMoodMessageContent(JavaScriptCheck.javaScriptCheck(moodMessage.getMoodMessageContent()));
        moodMessage = moodMessageService.publishMoodMessageReply(moodMessage, respondent);

        return moodMessageService.MoodMessageNewReply(moodMessage, answerer, respondent);
    }


    /**
     * 获得最新的（每个页面为n条）的所有留言，使用了pagehelper分页助手
     */
    @GetMapping("/newMoodWord")
    @ResponseBody
    public JSONObject newMoodWord(@RequestParam("rows") String rows,
                                   @RequestParam("pageNum") String pageNum){
        return moodMessageService.findFiveNewComment(Integer.parseInt(rows),Integer.parseInt(pageNum));
    }


    /**
     * 点赞
     * @return 点赞数
     */
    @GetMapping("/addMoodMessageLike")
    public int addMoodMessageLike(@RequestParam("pageName") String pageName,
                                   @RequestParam("respondentId") String respondentId,//被回复的那条message的 ID主键
                                   @RequestParam ("answerer") String answerer){

        String username;
        TimeUtil timeUtil = new TimeUtil();
        int userId = userService.findIdByUsername(answerer);
        MoodMessageLikesRecord moodMessageLikesRecord = new MoodMessageLikesRecord(pageName, Integer.parseInt(respondentId), userId, timeUtil.getFormatDateForFive());//去除resID.缩进1
        if(moodMessageLikesRecordService.isLiked(moodMessageLikesRecord.getPageName(), moodMessageLikesRecord.getPId(), userId)){
            logger.info("This user had clicked good for this page");
            return -2;
        }
        int likes = moodMessageService.updateLikeByPageNameAndId(pageName, moodMessageLikesRecord.getPId());
        moodMessageLikesRecordService.insertMoodMessageLikesRecord(moodMessageLikesRecord);
        return likes;
    }



//    @RequestMapping("/moodplaza")
//    public String get_moodplaza(Model model){
//
//        model.addAttribute("mood_01", "today is so beautiful day!");
//        return "MoodPlaza";
//
//    }
}
