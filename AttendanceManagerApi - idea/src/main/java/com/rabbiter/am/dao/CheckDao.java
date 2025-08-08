package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Check;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckDao {
    int deleteById(String id);

    int insert(Check check);

    Check selectById(String id);

    int update(Check record);

    List<Check> findByNumber(@Param("number")String number);

    List<Check> findByMonth(@Param("month")String month);

    List<Check> findByNumberAndMonth(Check check);

    Check findByNumberAndDate(Check check);

    List<Check> getCheckDay(Check check);

    List<Check> getLateDay(Check check);

    List<Check> getLeaveEarlyDay(Check check);

    List<Check> getLeaveDay(Check check);
}