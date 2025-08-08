package com.rabbiter.am.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.rabbiter.am.entity.Check;
import com.rabbiter.am.entity.Employee;
import com.rabbiter.am.entity.ExportCheck;
import com.rabbiter.am.service.CheckService;
import com.rabbiter.am.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/check")
public class CheckController {

    @Autowired
    private CheckService checkService;
    @Autowired
    private EmployeeService employeeService;

    @ResponseBody
    @RequestMapping(value = "/checkOn",method = RequestMethod.POST)
    public int checkOn(@RequestBody Check check) throws ParseException {
        return checkService.checkOn(check);
    }

    @ResponseBody
    @RequestMapping(value = "/checkOff",method = RequestMethod.POST)
    public int checkOff(@RequestBody Check check) throws ParseException {
        return checkService.checkOff(check);
    }

    @ResponseBody
    @RequestMapping(value = "/getCheckOn",method = RequestMethod.POST)
    public int getCheckOn(@RequestBody Check check) {
        return checkService.getCheckOn(check);
    }

    @ResponseBody
    @RequestMapping(value = "/getCheckOff",method = RequestMethod.POST)
    public int getCheckOff(@RequestBody Check check) {
        return checkService.getCheckOff(check);
    }

    @ResponseBody
    @RequestMapping(value = "/findByNumber",method = RequestMethod.POST)
    public List<Check> findByNumber(@RequestBody Check check){
        return checkService.findByNumber(check.getEmployeeID());
    }

    @ResponseBody
    @RequestMapping(value = "/findByMonth",method = RequestMethod.POST)
    public List<Check> findByMonth(@RequestBody Check check){
        return checkService.findByMonth(check.getMonth());
    }

    @ResponseBody
    @RequestMapping(value = "/findByNumberAndMonth",method = RequestMethod.POST)
    public List<Check> findByNumberAndMonth(@RequestBody Check check){
        return checkService.findByNumberAndMonth(check);
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkDay",method = RequestMethod.GET)
    public int getWorkDay(){
        return checkService.getWorkDay("2021-05");
    }

    @ResponseBody
    @RequestMapping(value = "/getCheckInfo",method = RequestMethod.POST)
    public Check getCheckInfo(@RequestBody Check check){
        Check check1 = new Check();
        check1.setCheckDays(checkService.getCheckDayNumber(check));
        check1.setWorkDays(checkService.getWorkDay(check.getMonth()));
        check1.setLateDays(checkService.getLateDayNumber(check));
        check1.setLeaveEarlyDays(checkService.getLeaveEarlyDayNumber(check));
        String leaveType = "";
        leaveType = "事假";
        check1.setLeaveDays1(checkService.getLeaveDayNumber(check,leaveType));
        leaveType = "婚假";
        check1.setLeaveDays2(checkService.getLeaveDayNumber(check,leaveType));
        leaveType = "丧假";
        check1.setLeaveDays3(checkService.getLeaveDayNumber(check,leaveType));
        leaveType = "产假";
        check1.setLeaveDays4(checkService.getLeaveDayNumber(check,leaveType));
        leaveType = "陪产假";
        check1.setLeaveDays5(checkService.getLeaveDayNumber(check,leaveType));
        leaveType = "病假";
        check1.setLeaveDays6(checkService.getLeaveDayNumber(check,leaveType));
        return check1;
    }

    @ResponseBody
    @RequestMapping(value = "/getCheckList",method = RequestMethod.POST)
    public List<Check> getCheckList(@RequestBody Check check){
        List<Check> checkList = new ArrayList<>();
        List<Employee> employeeList = employeeService.getAll();
        for(Employee item : employeeList){
            Check check1 = new Check();
            Check check2 = new Check();
            check2.setEmployeeID(item.getNumber());
            check2.setDate(check.getDate());
            check2.setMonth(check.getMonth());
            check1.setEmployeeID(item.getNumber());
            check1.setEmployeeName(item.getName());
            check1.setCheckDays(checkService.getCheckDayNumber(check2));
            check1.setWorkDays(checkService.getWorkDay(check2.getMonth()));
            check1.setLateDays(checkService.getLateDayNumber(check2));
            check1.setLeaveEarlyDays(checkService.getLeaveEarlyDayNumber(check2));
            check1.setLeaveDays(checkService.getAllLeaveDay(check2));
            checkList.add(check1);
        }
        return checkList;
    }

    @RequestMapping(value = "/exportExcel",method = RequestMethod.GET)
    public void export(HttpServletResponse response,@RequestParam String month) throws IOException {
        Check check = new Check();
        check.setDate(month);
        check.setMonth(month);
        List<Check> checkList = getCheckList(check);
        List<ExportCheck> exportCheckList = new ArrayList<>();
        for(Check item : checkList){
            ExportCheck exportCheck = new ExportCheck();
            exportCheck.setEmployeeID(item.getEmployeeID());
            exportCheck.setEmployeeName(item.getEmployeeName());
            exportCheck.setWorkDays(item.getWorkDays());
            exportCheck.setCheckDays(item.getCheckDays());
            exportCheck.setLeaveDays(item.getLeaveDays());
            exportCheck.setLateDays(item.getLateDays());
            exportCheck.setLeaveEarlyDays(item.getLeaveEarlyDays());
            exportCheckList.add(exportCheck);
        }
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        String fileName = "员工考勤表"+month.substring(0,7);
        Sheet sheet = new Sheet(1, 0,ExportCheck.class);
        //设置自适应宽度
        sheet.setAutoWidth(Boolean.TRUE);
        // 第一个 sheet 名称
        sheet.setSheetName("员工考勤表"+month.substring(0,7));
        writer.write(exportCheckList, sheet);
        //通知浏览器以附件的形式下载处理，设置返回头要注意文件名有中文
        response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xlsx");
        writer.finish();
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        out.flush();
    }
}
