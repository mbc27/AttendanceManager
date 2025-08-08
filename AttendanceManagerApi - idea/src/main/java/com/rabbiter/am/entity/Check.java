package com.rabbiter.am.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Check {
    private String id;

    private String employeeID;

    private String employeeName;

    private String date;

    private String remarks;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkOnTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkOffTime;

    private String checkOnStatus;

    private String checkOffStatus;

    private String month;

    private Integer workDays;

    private Integer checkDays;

    private Integer lateDays;

    private Integer leaveEarlyDays;

    private Integer days;

    private Integer leaveDays;

    private Integer leaveDays1;

    private Integer leaveDays2;

    private Integer leaveDays3;

    private Integer leaveDays4;

    private Integer leaveDays5;

    private Integer leaveDays6;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID == null ? null : employeeID.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getCheckOnTime() {
        return checkOnTime;
    }

    public void setCheckOnTime(Date checkOnTime) {
        this.checkOnTime = checkOnTime;
    }

    public Date getCheckOffTime() {
        return checkOffTime;
    }

    public void setCheckOffTime(Date checkOffTime) {
        this.checkOffTime = checkOffTime;
    }

    public String getCheckOnStatus() {
        return checkOnStatus;
    }

    public void setCheckOnStatus(String checkOnStatus) {
        this.checkOnStatus = checkOnStatus;
    }

    public String getCheckOffStatus() {
        return checkOffStatus;
    }

    public void setCheckOffStatus(String checkOffStatus) {
        this.checkOffStatus = checkOffStatus;
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getLeaveDays1() {
        return leaveDays1;
    }

    public void setLeaveDays1(Integer leaveDays1) {
        this.leaveDays1 = leaveDays1;
    }

    public Integer getLeaveDays2() {
        return leaveDays2;
    }

    public void setLeaveDays2(Integer leaveDays2) {
        this.leaveDays2 = leaveDays2;
    }

    public Integer getLeaveDays3() {
        return leaveDays3;
    }

    public void setLeaveDays3(Integer leaveDays3) {
        this.leaveDays3 = leaveDays3;
    }

    public Integer getLeaveDays4() {
        return leaveDays4;
    }

    public void setLeaveDays4(Integer leaveDays4) {
        this.leaveDays4 = leaveDays4;
    }

    public Integer getLeaveDays5() {
        return leaveDays5;
    }

    public void setLeaveDays5(Integer leaveDays5) {
        this.leaveDays5 = leaveDays5;
    }

    public Integer getLeaveDays6() {
        return leaveDays6;
    }

    public void setLeaveDays6(Integer leaveDays6) {
        this.leaveDays6 = leaveDays6;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}