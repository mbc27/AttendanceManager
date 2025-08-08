package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Meeting;
import com.rabbiter.am.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Meeting> get(){
        return meetingService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Meeting meeting){
        int size = meetingService.insert(meeting);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Meeting meeting){
        return meetingService.update(meeting);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public int deleteById(@RequestBody Meeting meeting){
        System.out.println(meeting.getId());
        return meetingService.deleteById(meeting.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/getAllMeeting",method = RequestMethod.GET)
    public List<Meeting> getAllMeeting(){
        return meetingService.getAllMeeting();
    }

    @ResponseBody
    @RequestMapping(value = "/getAllNotice",method = RequestMethod.GET)
    public List<Meeting> getAllNotice(){
        return meetingService.getAllNotice();
    }

    @ResponseBody
    @RequestMapping(value = "/getNotice",method = RequestMethod.GET)
    public List<Meeting> getNotice(){
        return meetingService.getNotice();
    }
}
