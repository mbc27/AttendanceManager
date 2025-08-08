package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PositionDao {
    int deleteById(String id);

    int insert(Position record);

    Position selectById(String id);

    int update(Position record);

    Position findByNumber(@Param("number")String number);

    List<Position> findByDepartmentID(@Param("departmentID")String departmentID);
}