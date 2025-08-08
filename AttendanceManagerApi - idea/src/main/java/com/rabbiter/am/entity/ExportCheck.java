package com.rabbiter.am.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ExportCheck extends BaseRowModel {

    @ExcelProperty(value = "员工编号", index = 0)
    private String employeeID;

    @ExcelProperty(value = "员工姓名", index = 1)
    private String employeeName;

    @ExcelProperty(value = "应出勤（天）", index = 2)
    private Integer workDays;

    @ExcelProperty(value = "实际出勤", index = 3)
    private Integer checkDays;

    @ExcelProperty(value = "迟到（次）", index = 5)
    private Integer lateDays;

    @ExcelProperty(value = "早退（次）", index = 6)
    private Integer leaveEarlyDays;

//    private Integer days;

    @ExcelProperty(value = "请假（天）", index = 4)
    private Integer leaveDays;

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Integer workDays) {
        this.workDays = workDays;
    }

    public Integer getCheckDays() {
        return checkDays;
    }

    public void setCheckDays(Integer checkDays) {
        this.checkDays = checkDays;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public Integer getLeaveEarlyDays() {
        return leaveEarlyDays;
    }

    public void setLeaveEarlyDays(Integer leaveEarlyDays) {
        this.leaveEarlyDays = leaveEarlyDays;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }
}
