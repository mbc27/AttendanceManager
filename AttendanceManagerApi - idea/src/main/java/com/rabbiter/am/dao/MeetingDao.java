package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingDao {
    int deleteById(String id);

    int insert(Meeting record);

    Meeting selectById(String id);

    int update(Meeting record);

    List<Meeting> getAll();

    List<Meeting> getAllMeeting();

    List<Meeting> getAllNotice();

    List<Meeting> getNotice();
}