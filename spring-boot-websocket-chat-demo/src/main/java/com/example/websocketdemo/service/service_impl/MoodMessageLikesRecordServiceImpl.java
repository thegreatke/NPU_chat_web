package com.example.websocketdemo.service.service_impl;

import com.example.websocketdemo.mapper.MoodMessageLikesRecordMapper;
import com.example.websocketdemo.model.MoodMessageLikesRecord;
import com.example.websocketdemo.service.MoodMessageLikesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: TheGreatKe
 * @Date: 2018/7/16 15:32
 * Describe:
 */
@Service
public class MoodMessageLikesRecordServiceImpl implements MoodMessageLikesRecordService {

    @Autowired
    MoodMessageLikesRecordMapper MoodMessageLikesRecordMapper;

    //返回一个是会否点赞的判断
    @Override
    public boolean isLiked(String pageName, int pId, int likeId) {

        return MoodMessageLikesRecordMapper.isLiked(pageName, pId, likeId) != null;
    }
    //添加一个点赞记录
    @Override
    public void insertMoodMessageLikesRecord(MoodMessageLikesRecord MoodMessageLikesRecord) {
        MoodMessageLikesRecordMapper.insertMoodMessageLikesRecord(MoodMessageLikesRecord);
    }
}
