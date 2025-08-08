package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Customer;
import com.rabbiter.am.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Customer> get(){
        return customerService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Customer customer){
        int size = customerService.insert(customer);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @ResponseBody
    @RequestMapping(value = "/findByName",method = RequestMethod.GET)
    public List<Customer> findByName(@RequestParam String name){
        return customerService.findByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public int deleteById(@RequestBody Customer customer){
        return customerService.deleteById(customer.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/findByNameAndType",method = RequestMethod.POST)
    public List<Customer> findByNameAndType(@RequestBody Customer customer){
        return customerService.findByNameAndType(customer);
    }

    @ResponseBody
    @RequestMapping(value = "/findByApplyNumber",method = RequestMethod.POST)
    public List<Customer> findByApplyNumber(@RequestBody Customer customer){
        return customerService.findByApplyNumber(customer.getApplyNumber());
    }
}
