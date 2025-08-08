package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Department;
import com.rabbiter.am.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Department> get(){
        return departmentService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Department department){
        int size = departmentService.insert(department);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Department department){
//        int size = departmentService.update(department);
//        if(size == 1){
//            return Result.failure(ResultCode.USER_HAS_EXISTED);
//        }else{
//            return Result.success();
//        }
        return departmentService.update(department);
    }

    @ResponseBody
    @RequestMapping(value = "/findByName",method = RequestMethod.GET)
    public List<Department> findByName(@RequestParam String name){
        return departmentService.findByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public Result deleteById(@RequestBody Department department){
        int size = departmentService.deleteById(department.getId());
        if(size == 0){
            return Result.success();
        }else if(size == 1){
            return Result.failure(ResultCode.DATA_ALREADY_EXISTED);
        }else {
            return Result.failure(ResultCode.SERVER_ERROR);
        }
    }
}
