package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Apply;
import com.rabbiter.am.entity.Task;
import com.rabbiter.am.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<Task> get(@RequestBody Task task){
        String receiveNumber = task.getReceiveNumber();
        System.out.println("获取任务列表 - 接收者工号: " + receiveNumber);
        List<Task> taskList = taskService.getAll(receiveNumber);
        System.out.println("获取任务列表结果 - 任务数量: " + (taskList == null ? 0 : taskList.size()));
        if (taskList != null && !taskList.isEmpty()) {
            System.out.println("任务列表中第一条数据: ID=" + taskList.get(0).getId() + ", 名称=" + taskList.get(0).getName());
        }
        return taskList;
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Task task){
        int size = taskService.insert(task);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Task task){
        return taskService.update(task);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public int deleteById(@RequestBody Task task){
        System.out.println(task.getId());
        return taskService.deleteById(task.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/getAllTodo",method = RequestMethod.POST)
    public List<Task> getAllTodo(@RequestBody Task task){
        return taskService.getAllTodo(task.getReceiveNumber());
    }

    @ResponseBody
    @RequestMapping(value = "/getAllHistoric",method = RequestMethod.POST)
    public List<Task> getAllHistoric(@RequestBody Task task){
        return taskService.getAllHistoric(task.getReceiveNumber());
    }

    @ResponseBody
    @RequestMapping(value = "/findByApplyID",method = RequestMethod.POST)
    public Apply findByApplyID(@RequestBody Task task){
        return taskService.findByApplyID(task);
    }

    @ResponseBody
    @RequestMapping(value = "/approval",method = RequestMethod.POST)
    public Result approval(@RequestBody Task task) throws ParseException {
        int size = taskService.approval(task);
        if(size == 0){
            return Result.success();
        }else{
            return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
