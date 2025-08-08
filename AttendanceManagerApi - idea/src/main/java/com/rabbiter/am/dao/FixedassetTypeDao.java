package com.rabbiter.am.dao;

import com.rabbiter.am.entity.FixedassetType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FixedassetTypeDao {
    int deleteById(String id);

    int insert(FixedassetType record);

    FixedassetType selectById(String id);

    int update(FixedassetType record);

    List<FixedassetType> getAll();
}