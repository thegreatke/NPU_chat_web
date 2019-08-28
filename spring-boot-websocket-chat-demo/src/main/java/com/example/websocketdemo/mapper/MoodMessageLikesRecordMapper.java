package com.example.websocketdemo.mapper;

import com.example.websocketdemo.model.MoodMessageLikesRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: TheGreatKe
 * @Date: 2018/7/16 15:33
 * Describe: 留言点赞sql
 */
@Mapper
@Repository
public interface MoodMessageLikesRecordMapper {

    @Select("select likeDate from leave_message_likes_record where pageName=#{pageName} and pId=#{pId} and likerId=#{likerId}")
    MoodMessageLikesRecord isLiked(@Param("pageName") String pageName, @Param("pId") int pId, @Param("likerId") int likerId);

    @Insert("insert into leave_message_likes_record(pageName,pId,likerId,likeDate) " +
            "values(#{pageName},#{pId},#{likerId},#{likeDate})")
    void insertMoodMessageLikesRecord(MoodMessageLikesRecord MoodMessageLikesRecord);
}
