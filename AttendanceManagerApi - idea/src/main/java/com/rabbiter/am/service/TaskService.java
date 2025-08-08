package com.rabbiter.am.service;

import com.rabbiter.am.dao.FixedassetsDao;
import com.rabbiter.am.dao.LeaveDao;
import com.rabbiter.am.dao.TaskDao;
import com.rabbiter.am.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private LeaveDao leaveDao;
    @Autowired
    private FixedassetsDao fixedassetsDao;
    @Autowired
    private CheckService checkService;
    @Autowired
    private LeaveTypeService leaveTypeService;
    @Autowired
    private FixedassetTypeService fixedassetTypeService;
    @Autowired
    private EmployeeService employeeService;

    public int deleteById(String id) {
        return taskDao.deleteById(id);
    }

    public int insert(Task task) {
        // 确保任务分配给管理岗位的人员
        try {
            // 查找所有管理岗位人员
            List<Employee> managers = employeeService.findByType("3");
            if (managers != null && !managers.isEmpty()) {
                // 分配给第一个管理岗位人员
                task.setReceiveNumber(managers.get(0).getNumber());
            }
        } catch (Exception e) {
            System.err.println("分配任务给管理员时出错: " + e.getMessage());
        }
        
        taskDao.insert(task);
        return 0;
    }

    public Task selectById(String id) {
        return taskDao.selectById(id);
    }

    public int update(Task task) {
        return taskDao.update(task);
    }

    public List<Task> getAllTodo(String receiveNumber) {
        // 如果查询者是管理员，返回所有待办任务
        Employee employee = employeeService.findByNumber(receiveNumber);
        if (employee != null && "3".equals(employee.getEmployeeType())) {
            return taskDao.getAllTodo(null); // 传null表示获取所有待办任务
        }
        return taskDao.getAllTodo(receiveNumber);
    }

    public List<Task> getAllHistoric(String receiveNumber) {
        // 如果查询者是管理员，返回所有历史任务
        Employee employee = employeeService.findByNumber(receiveNumber);
        if (employee != null && "3".equals(employee.getEmployeeType())) {
            return taskDao.getAllHistoric(null); // 传null表示获取所有历史任务
        }
        return taskDao.getAllHistoric(receiveNumber);
    }

    public Apply findByApplyID(Task task){
        //task.setStatus(task.getStatus());
        task.setType(taskTypeService.selectById(task.getTypeID()).getName());
        return taskDao.findByApplyID(task);
    }

    public List<Task> getAll(String receiveNumber) {
        // 如果查询者是管理员或管理岗位，返回所有任务
        Employee employee = employeeService.findByNumber(receiveNumber);
        List<Task> taskList;
        
        if (employee != null && "3".equals(employee.getEmployeeType())) {
            // 管理员可以查看所有任务
            taskList = taskDao.getAll(null); // 传null表示获取所有任务
            System.out.println("管理员正在查看所有任务，共" + taskList.size() + "条");
        } else {
            // 其他人只能查看自己的任务
            taskList = taskDao.getAll(receiveNumber);
            System.out.println("普通用户正在查看自己的任务，共" + taskList.size() + "条");
        }
        
        for(Task item : taskList){
            if(item.getStatus().equals("0")){
                item.setStatusName("待审批");
            }else if(item.getStatus().equals("1")){
                item.setStatusName("已通过");
            }else if(item.getStatus().equals("2")){
                item.setStatusName("已驳回");
            }
        }
        return taskList;
    }

    public int approval(Task task) throws ParseException {
        task.setType(taskTypeService.selectById(task.getTypeID()).getName());
        Apply apply = taskDao.findByApplyID(task);
        String type = taskTypeService.selectById(task.getTypeID()).getName();
        if(task.getAdvice().equals("yes")){
            task.setStatus("1");
            taskDao.update(task);
            if(type.equals("请假申请")){
                Leave leave = leaveDao.selectById(apply.getId());
                leave.setStatus("1");
                leaveDao.update(leave);
                //循环从开始日期到结束日期中间的所有日期
                List<Check> checkList = new ArrayList<>();
                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                String date1 = sdf.format(leave.getBeginTime());
                String date2 = sdf.format(leave.getEndTime());
                Date beginDate = sdf.parse(date1);
                Date endDate = sdf.parse(date2);
                List<String> dateList = new ArrayList<>();
                dateList.add(date1);
                Calendar calBegin = Calendar.getInstance();
                calBegin.setTime(beginDate);
                Calendar calEnd = Calendar.getInstance();
                calEnd.setTime(endDate);
                while (endDate.after(calBegin.getTime())){
                    calBegin.add(Calendar.DAY_OF_MONTH,1);
                    dateList.add(sdf.format(calBegin.getTime()));
                }
                for(String item : dateList){
                    Check check = new Check();
                    check.setDate(item);
                    checkList.add(check);
                }
                String remarks = leaveTypeService.selectById(leave.getTypeID()).getName();
                for(Check item : checkList){
                    item.setId(UUID.randomUUID().toString());
                    item.setEmployeeID(leave.getApplyNumber());
                    item.setRemarks(remarks);
                    checkService.insert(item);
                }
            }else if(type.equals("固定资产购置申请") || type.equals("固定资产报废申请")){
                Fixedassets fixedassets = fixedassetsDao.selectById(apply.getId());
                fixedassets.setStatus("1");
                fixedassetsDao.update(fixedassets);
                FixedassetType fixedassetType = fixedassetTypeService.selectById(fixedassets.getTypeID());
                fixedassetType.setQuantity(fixedassetType.getQuantity()+1);
            }
            return 0;
        }else if(task.getAdvice().equals("no")){
            task.setStatus("2");
            taskDao.update(task);
            if(type.equals("请假申请")){
                Leave leave = leaveDao.selectById(apply.getId());
                leave.setStatus("2");
                leaveDao.update(leave);
            }else if(type.equals("固定资产购置申请") || type.equals("固定资产报废申请")){
                Fixedassets fixedassets = fixedassetsDao.selectById(apply.getId());
                fixedassets.setStatus("2");
                fixedassetsDao.update(fixedassets);
            }
            return 0;
        }else {
            return 1;
        }
    }
}
