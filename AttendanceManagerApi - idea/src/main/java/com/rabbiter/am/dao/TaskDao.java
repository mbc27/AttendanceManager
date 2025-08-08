package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Apply;
import com.rabbiter.am.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskDao {
    int deleteById(String id);

    int insert(Task record);

    Task selectById(String id);

    int update(Task record);

    /**
     * 获取所有待办任务
     * @param receiveNumber 接收者工号，如果为null则返回所有待办任务
     * @return 待办任务列表
     */
    List<Task> getAllTodo(@Param("receiveNumber")String receiveNumber);

    /**
     * 获取所有历史任务
     * @param receiveNumber 接收者工号，如果为null则返回所有历史任务
     * @return 历史任务列表
     */
    List<Task> getAllHistoric(@Param("receiveNumber")String receiveNumber);

    Apply findByApplyID(Task task);

    /**
     * 获取所有任务
     * @param receiveNumber 接收者工号，如果为null则返回所有任务
     * @return 任务列表
     */
    List<Task> getAll(@Param("receiveNumber")String receiveNumber);
}