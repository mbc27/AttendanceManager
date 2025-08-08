package com.rabbiter.am.service;

import com.rabbiter.am.dao.EmployeeDao;
import com.rabbiter.am.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeTypeService employeeTypeService;
    @Autowired
    private PositionService positionService;

    public int deleteById(String id) {
        Employee employee = employeeDao.selectById(id);
        employeeDao.deleteById(id);
        syncDepartment(employee);
        return 200;
    }

    private void syncDepartment(Employee employee) {
        if (employee.getDepartmentID() != null) {
            List<Employee> employees = employeeDao.selectByDepartmentId(employee.getDepartmentID());
            Department department = departmentService.selectById(employee.getDepartmentID());
            department.setQuantity(employees.size());
            departmentService.update(department);
        }
        Position position = null;
        if (employee.getPosition() != null) {
            List<Employee> employees = employeeDao.selectByPositionId(employee.getPosition());
            position = positionService.selectById(employee.getPosition());
            position.setQuantity(employees.size());
            positionService.update(position);
        }
        if (position != null) {
            List<Employee> employees = employeeDao.selectByEmployeeType(position.getTypeID());
            EmployeeType employeeType = employeeTypeService.selectById(position.getTypeID());
            employeeType.setQuantity(employees.size());
            employeeTypeService.update(employeeType);
        }
    }

    public int insert(Employee employee) {
        Employee employee1 = findByNumber(employee.getNumber());
        if (employee1 != null) {
            return 1;
        } else {

            Position position = positionService.selectById(employee.getPosition());
            if(position != null) {
                employee.setEmployeeType(position.getTypeID());
            }
            employee.setId(UUID.randomUUID().toString());
            employee.setPassword("123456");
            employee.setWorkStatus("0");
            employeeDao.insert(employee);
            syncDepartment(employee);
            return 0;
        }
    }

    public Employee selectById(String id) {
        return employeeDao.selectById(id);
    }

    public int update(Employee employee) {
        Position position = positionService.selectById(employee.getPosition());
        if(position != null) {
            employee.setEmployeeType(position.getTypeID());
        }
        employeeDao.update(employee);
        syncDepartment(employee);
        return 1;
    }

    public List<Employee> getAll() {
        return employeeDao.getAll();
    }


    public Employee findByNumber(String number) {
        return employeeDao.findByNumber(number);
    }

    public Employee findByPassword(String password) {
        return employeeDao.findByPassword(password);
    }

    public List<Employee> findByNameAndDepartment(Employee employee) {
        return employeeDao.findByNameAndDepartment(employee);
    }

    public Employee getMinister(Employee employee) {
        return employeeDao.getMinister(employee);
    }

    public Employee getBoss(Employee employee) {
        try {
            return employeeDao.getBoss(employee);
        } catch (Exception e) {
            System.err.println("获取老板信息出错: " + e.getMessage());
            return null;
        }
    }

    public Employee getLeader(Employee employee) {
        try {
            if (employee == null) {
                System.err.println("获取领导信息失败：员工信息为空");
                return null;
            }
            
            String employeeType = employee.getType();
            if (employeeType == null) {
                System.err.println("员工类型为空，无法确定上级领导");
                return null;
            }
            
            if (employeeType.equals("1")) {
                // 普通员工，获取部门主管
                Employee minister = getMinister(employee);
                if (minister != null) {
                    return minister;
                } else {
                    // 如果没有部门主管，尝试获取老板
                    System.out.println("未找到部门主管，尝试获取公司老板");
                    Employee boss = getBoss(employee);
                    return boss;
                }
            } else if (employeeType.equals("2")) {
                // 部门主管，获取老板
                Employee boss = getBoss(employee);
                return boss;
            } else {
                // 如果是老板或无法识别的类型，返回员工自身
                return employee;
            }
        } catch (Exception e) {
            System.err.println("获取领导信息出错: " + e.getMessage());
            e.printStackTrace();
            // 出现异常时返回null，调用方需要处理这种情况
            return null;
        }
    }

    public List<Chart> getEducation() {
        return employeeDao.getEducation();
    }

    public List<Employee> findByPositionID(String positionID) {
        return employeeDao.findByPositionID(positionID);
    }

    public List<Chart> getAge() {
        return employeeDao.getAge();
    }

    public List<Chart> getNew() {
        return employeeDao.getNew();
    }

    /**
     * 根据员工类型查找员工
     * @param type 员工类型 (1-普通员工, 2-部门主管, 3-总经理/管理员)
     * @return 员工列表
     */
    public List<Employee> findByType(String type) {
        try {
            return employeeDao.findByType(type);
        } catch (Exception e) {
            System.err.println("根据类型查找员工出错: " + e.getMessage());
            return null;
        }
    }

}
