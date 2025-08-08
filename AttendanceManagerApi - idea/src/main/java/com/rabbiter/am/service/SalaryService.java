package com.rabbiter.am.service;

import com.rabbiter.am.dao.SalaryDao;
import com.rabbiter.am.entity.Check;
import com.rabbiter.am.entity.Employee;
import com.rabbiter.am.entity.Position;
import com.rabbiter.am.entity.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SalaryService {

    @Autowired
    private SalaryDao salaryDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;

    public int deleteById(String id) {
        return 0;
    }

    public int insert(Salary salary) {
        salaryDao.insert(salary);
        return 0;
    }

    public Salary selectById(String id) {
        return null;
    }

    public int update(Salary record) {
        return 0;
    }

    public int payOff(Check check){
        if(findByNumberAndMonth(check) != null){
            return 1;
        }else {
            Salary salary = new Salary();
            salary.setId(UUID.randomUUID().toString());
            Employee employee = employeeService.findByNumber(check.getEmployeeID());
            Position position = positionService.selectById(employee.getPosition());
            salary.setMonthlySalary(position.getMonthlySalary());
            salary.setWorkDays(check.getWorkDays());
            salary.setCheckDays(check.getCheckDays());
            salary.setLeaveDays(check.getLeaveDays());
            salary.setLateTimes(check.getLateDays());
            salary.setLeaveEarlyTimes(check.getLeaveEarlyDays());
            int monthlySalary = salary.getMonthlySalary();
            int workDays = salary.getWorkDays();
            int checkDays = salary.getCheckDays();
            int lateTimes = salary.getLateTimes();
            int leaveEarlyTimes = salary.getLeaveEarlyTimes();
            double salarys = monthlySalary/workDays*checkDays-lateTimes*50-leaveEarlyTimes*50;
            salary.setSalary(salarys);
            salary.setEmployeeID(check.getEmployeeID());
            salary.setMonth(check.getMonth().substring(0,7));
            insert(salary);
            return 0;
        }
    }

    public Salary findByNumberAndMonth(Check check){
        check.setMonth(check.getMonth().substring(0,7));
        return salaryDao.findByNumberAndMonth(check);
    }
}
