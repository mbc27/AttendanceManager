package com.rabbiter.am.service;

import com.rabbiter.am.dao.DepartmentDao;
import com.rabbiter.am.entity.Department;
import com.rabbiter.am.entity.Employee;
import com.rabbiter.am.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;

    public int deleteById(String id) {
        Employee employee = new Employee();
        employee.setDepartmentID(id);
        System.out.println("id:"+id);
        System.out.println("id2:"+employee.getDepartmentID());
        System.out.println("employee:"+employeeService.findByNameAndDepartment(employee));
        System.out.println("size:"+employeeService.findByNameAndDepartment(employee).size());
        if(employeeService.findByNameAndDepartment(employee).size() != 0){
            return 1;
        }else {
            departmentDao.deleteById(id);
            List<Position> positionList = positionService.findByDepartmentID(id);
            for(Position item: positionList){
                positionService.deleteById(item.getId());
            }
            return 0;
        }
    }

    public int insert(Department department) {
        Department department1 = findByNumber(department.getNumber());
        if(department1 != null) {
            return 1;
        }else {
            department.setId(UUID.randomUUID().toString());
            department.setQuantity(0);
            department.setPosNum(0);
            departmentDao.insert(department);
            return 0;
        }
    }

    public Department selectById(String id) {
        return departmentDao.selectById(id);
    }

    public int update(Department department) {
        return departmentDao.update(department);
    }

    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    public List<Department> findByName(String name) {
        return departmentDao.findByName(name);
    }

    public Department findByNumber(String number) {
        return departmentDao.findByNumber(number);
    }
}
