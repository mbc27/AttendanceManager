package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Chart;
import com.rabbiter.am.entity.Employee;
import com.rabbiter.am.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Employee> get(){
        return employeeService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Employee employee){
        int size = employeeService.insert(employee);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Employee employee){
        return employeeService.update(employee);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public int deleteById(@RequestBody Employee employee){
        return employeeService.deleteById(employee.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/findByNameAndDepartment",method = RequestMethod.POST)
    public List<Employee> findByNameAndDepartment(@RequestBody Employee employee){
        return employeeService.findByNameAndDepartment(employee);
    }

    @ResponseBody
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public int updatePassword(@RequestBody Employee employee){
        Employee employee1 = employeeService.findByNumber(employee.getNumber());
        employee1.setPassword(employee.getPassword());
        return employeeService.update(employee1);
    }

    @ResponseBody
    @RequestMapping(value = "/findByNumber",method = RequestMethod.POST)
    public Employee findByNumber(@RequestBody Employee employee){
        System.out.println("查询员工工号: " + employee.getNumber());
        Employee result = employeeService.findByNumber(employee.getNumber());
        if (result != null) {
            System.out.println("员工信息: 工号=" + result.getNumber() + ", 姓名=" + result.getName() + 
                ", 类型=" + result.getEmployeeType() + ", 职位=" + result.getPosition());
        } else {
            System.out.println("未找到工号为 " + employee.getNumber() + " 的员工");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getEducation",method = RequestMethod.GET)
    public List<Chart> getEducation(){
        return employeeService.getEducation();
    }

    @ResponseBody
    @RequestMapping(value = "/getAge",method = RequestMethod.GET)
    public List<Chart> getAge(){
        return employeeService.getAge();
    }

    @ResponseBody
    @RequestMapping(value = "/getNew",method = RequestMethod.GET)
    public List<Chart> getNew(){
        return employeeService.getNew();
    }

    @ResponseBody
    @RequestMapping(value = "/getAllAdmins",method = RequestMethod.GET)
    public List<Employee> getAllAdmins(){
        return employeeService.findByType("3");
    }
}
