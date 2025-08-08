package com.rabbiter.am.service;

import com.rabbiter.am.dao.MeetingDao;
import com.rabbiter.am.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingService {

    @Autowired
    private MeetingDao meetingDao;

    public int deleteById(String id) {
        return meetingDao.deleteById(id);
    }

    public int insert(Meeting meeting) {
        meeting.setId(UUID.randomUUID().toString());
        return meetingDao.insert(meeting);
    }

    public Meeting selectById(String id) {
        return meetingDao.selectById(id);
    }

    public int update(Meeting meeting) {
        return meetingDao.update(meeting);
    }

    public List<Meeting> getAll() {
        return meetingDao.getAll();
    }

    public List<Meeting> getAllMeeting() {
        return meetingDao.getAllMeeting();
    }

    public List<Meeting> getAllNotice() {
        return meetingDao.getAllNotice();
    }

    public List<Meeting> getNotice() {
        List<Meeting> meetingList =  meetingDao.getNotice();
        for(Meeting item : meetingList){
            if(item.getPublishTime() != null){
                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(item.getPublishTime());
                item.setDate(date);
            }
        }
        return meetingList;
    }
}
