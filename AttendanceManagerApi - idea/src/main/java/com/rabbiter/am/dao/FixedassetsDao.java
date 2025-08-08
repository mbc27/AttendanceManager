package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Fixedassets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FixedassetsDao {
    int deleteById(String id);

    int insert(Fixedassets record);

    Fixedassets selectById(String id);

    int update(Fixedassets record);

    List<Fixedassets> getAll();

    Fixedassets findByNumber(@Param("number")String number);

    List<Fixedassets> findByEmployeeNumber(@Param("number")String number);

    List<Fixedassets> getRoomList();
}