package com.example.websocketdemo.mapper;

import com.example.websocketdemo.model.MoodMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: TheGreatKe
 * @Date: 2018/7/15 14:01
 * Describe: 留言sql
 */
@Mapper
@Repository
public interface MoodMessageMapper {

    @Insert("insert into leave_message_record(pageName,PId,answererId,respondentId,MoodMessageDate,likes,MoodMessageContent) " +
            "values(#{pageName},#{PId},#{answererId},#{respondentId},#{MoodMessageDate},#{likes},#{MoodMessageContent})")
    void publishMoodMessage(MoodMessage MoodMessage);

    @Select("select * from leave_message_record where pageName=#{pageName} and PId=#{PId} order by id desc")
    List<MoodMessage> findAllMoodMessage(@Param("pageName") String pageName, @Param("PId") int PId);

    @Select("select answererId,respondentId,MoodMessageDate,MoodMessageContent from leave_message_record where pageName=#{pageName} and PId=#{PId}")
    List<MoodMessage> findMoodMessageReplyByPageNameAndPid(@Param("pageName") String pageName, @Param("PId") int PId);

    @Update("update leave_message_record set likes=likes+1 where pageName=#{pageName} and id=#{id}")
    void updateLikeByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select IFNULL(max(likes),0) from leave_message_record where pageName=#{pageName} and id=#{id}")
    int findLikesByPageNameAndId(@Param("pageName") String pageName, @Param("id") int id);

    @Select("select PId,pageName,answererId,respondentId,MoodMessageDate,MoodMessageContent from leave_message_record where answererId=#{answererId} order by id desc")
    List<MoodMessage> getUserMoodMessage(@Param("answererId") int answererId);

    @Select("select count(*) from leave_message_record where PId=#{id}")
    int countReplyNumById(@Param("id") int id);

    @Select("select count(*) from leave_message_record where PId=#{id} and respondentId=#{respondentId}")
    int countReplyNumByIdAndRespondentId(@Param("id") int id, @Param("respondentId") int respondentId);

    @Select("select pageName,PId,answererId,respondentId,MoodMessageDate,MoodMessageContent from leave_message_record order by id desc")
    List<MoodMessage> findFiveNewLeaveWord();

    @Select("select count(*) from leave_message_record")
    int countMoodMessageNum();
}
