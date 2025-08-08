package com.rabbiter.am.controller;

import com.rabbiter.am.config.Result;
import com.rabbiter.am.config.ResultCode;
import com.rabbiter.am.entity.Fixedassets;
import com.rabbiter.am.service.FixedassetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/fixedasset")
public class FixedassetController {

    @Autowired
    FixedassetService fixedassetService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Fixedassets> get(){
        return fixedassetService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result insert(@RequestBody Fixedassets fixedassets){
        int size = fixedassetService.insert(fixedassets);
        if(size == 1){
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        }else{
            return Result.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public int update(@RequestBody Fixedassets fixedassets){
        return fixedassetService.update(fixedassets);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public int deleteById(@RequestBody Fixedassets fixedassets){
        return fixedassetService.deleteById(fixedassets.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/findByEmployeeNumber",method = RequestMethod.POST)
    public List<Fixedassets> findByEmployeeNumber(@RequestBody Fixedassets fixedassets){
        return fixedassetService.findByEmployeeNumber(fixedassets.getEmployeeNumber());
    }

    @ResponseBody
    @RequestMapping(value = "/getRoomList",method = RequestMethod.GET)
    public List<Fixedassets> getRoomList(){
        return fixedassetService.getRoomList();
    }

    @ResponseBody
    @RequestMapping(value = "/getTaskByFixedassetID",method = RequestMethod.POST)
    public Result getTaskByFixedassetID(@RequestBody Fixedassets fixedassets){
        Fixedassets asset = fixedassetService.selectById(fixedassets.getId());
        if(asset != null && asset.getStatus().equals("0")) {
            return Result.success(asset.getApprovalID());
        } else {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public Result cancelAsset(@RequestBody Fixedassets fixedassets){
        Fixedassets asset = fixedassetService.selectById(fixedassets.getId());
        if(asset != null && asset.getStatus().equals("0")) {
            // 将状态更新为"已撤销"(3)
            asset.setStatus("3");
            fixedassetService.update(asset);
            return Result.success();
        } else {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }
}
