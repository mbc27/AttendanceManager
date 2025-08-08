package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentDao {
    int deleteById(String id);

    int insert(Department record);

    Department selectById(String id);

    int update(Department record);

    List<Department> getAll();

    List<Department> findByName(@Param("name")String name);

    Department findByNumber(@Param("number")String number);
}