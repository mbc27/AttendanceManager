package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Chart;
import com.rabbiter.am.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeDao {
    int deleteById(String id);

    int insert(Employee record);

    Employee selectById(String id);

    int update(Employee record);

    List<Employee> getAll();

//    List<Employee> findByName(@Param("name")String name);
//
//    List<Employee> findByDepartmentID(@Param("departmentID")String departmentID);

    Employee findByNumber(@Param("number")String number);

    Employee findByPassword(@Param("password")String password);

    List<Employee> findByNameAndDepartment(Employee employee);

    Employee getMinister(Employee employee);

    Employee getBoss(Employee employee);

    List<Chart> getEducation();

    List<Employee> findByPositionID(@Param("positionID")String positionID);

    List<Chart> getAge();

    List<Chart> getNew();

    List<Employee> selectByDepartmentId(@Param("departmentId") String departmentId);

    List<Employee> selectByPositionId(@Param("position") String position);

    List<Employee> selectByEmployeeType(@Param("employeeType") String employeeType);

    List<Employee> findByType(@Param("type") String type);
}