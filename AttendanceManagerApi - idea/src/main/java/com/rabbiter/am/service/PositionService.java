package com.rabbiter.am.service;

import com.rabbiter.am.dao.PositionDao;
import com.rabbiter.am.entity.Department;
import com.rabbiter.am.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PositionService {

    @Autowired
    private PositionDao positionDao;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    public int deleteById(String id) {
        if(employeeService.findByPositionID(id).size() != 0){
            return 1;
        }else {
            Position position = selectById(id);
            if(position.getDepartmentID() != null){
                Department department = departmentService.selectById(position.getDepartmentID());
                department.setPosNum(department.getPosNum()-1);
                departmentService.update(department);
            }
            positionDao.deleteById(id);
            return 0;
        }
    }

    public int insert(Position position) {
        Position position1 = findByNumber(position.getNumber());
        if(position1 != null) {
            return 1;
        }else {
            position.setId(UUID.randomUUID().toString());
            position.setQuantity(0);
            positionDao.insert(position);
            if(position.getDepartmentID() != null){
                Department department = departmentService.selectById(position.getDepartmentID());
                if(department != null) {

                    department.setPosNum(department.getPosNum()+1);
                    departmentService.update(department);
                }
            }
            return 0;
        }
    }

    public Position selectById(String id) {
        return positionDao.selectById(id);
    }

    public int update(Position position) {
        return positionDao.update(position);
    }

    public Position findByNumber(String number){
        return positionDao.findByNumber(number);
    }

    public List<Position> findByDepartmentID(String departmentID){
        return positionDao.findByDepartmentID(departmentID);
    }
}
