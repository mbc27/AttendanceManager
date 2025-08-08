package com.rabbiter.am.dao;

import com.rabbiter.am.entity.EmployeeType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeTypeDao {
    int deleteById(String id);

    int insert(EmployeeType record);

    EmployeeType selectById(String id);

    int update(EmployeeType record);

    List<EmployeeType> getAll();
}