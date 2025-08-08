package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Position;
import com.rabbiter.am.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Position position){
        int size = positionService.insert(position);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/findByDepartmentID",method = RequestMethod.POST)
    public List<Position> findByDepartmentID(@RequestBody Position position){
        return positionService.findByDepartmentID(position.getDepartmentID());
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public Result deleteById(@RequestBody Position position){
        int size = positionService.deleteById(position.getId());
        if(size == 0){
            return Result.success();
        }else if(size == 1){
            return Result.failure(ResultCode.DATA_ALREADY_EXISTED);
        }else {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Position position){
//        int size = positionService.update(position);
//        if(size == 1){
//            return Result.failure(ResultCode.USER_HAS_EXISTED);
//        }else{
//            return Result.success();
//        }
        return positionService.update(position);
    }
}
