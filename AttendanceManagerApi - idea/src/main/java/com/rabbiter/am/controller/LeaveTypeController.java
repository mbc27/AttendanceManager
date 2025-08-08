package com.rabbiter.am.controller;

import com.rabbiter.am.entity.LeaveType;
import com.rabbiter.am.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/leaveType")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<LeaveType> list(){
        return leaveTypeService.getAll();
    }
}
