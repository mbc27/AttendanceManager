package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Leave;
import com.rabbiter.am.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<Leave> get(@RequestBody Leave leave){
        System.out.println("请求员工请假列表，工号: " + leave.getApplyNumber());
        List<Leave> results = leaveService.findByEmployeeNumber(leave.getApplyNumber());
        System.out.println("返回请假记录数量: " + (results == null ? 0 : results.size()));
        return results;
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Leave leave){
        int size = leaveService.insert(leave);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Leave leave){
        return leaveService.update(leave);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public int deleteById(@RequestBody Leave leave){
        return leaveService.deleteById(leave.getId());
    }
}
