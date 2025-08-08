package com.rabbiter.am.dao;

import com.rabbiter.am.entity.TaskType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskTypeDao {
    int deleteById(String id);

    int insert(TaskType record);

    TaskType selectById(String id);

    int update(TaskType record);
}