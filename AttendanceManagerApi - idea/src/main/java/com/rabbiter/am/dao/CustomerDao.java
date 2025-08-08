package com.rabbiter.am.dao;

import com.rabbiter.am.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDao {
    int deleteById(String id);

    int insert(Customer record);

    Customer selectById(String id);

    int update(Customer record);

    List<Customer> getAll();

    List<Customer> findByName(@Param("name")String name);

    List<Customer> findByNameAndType(Customer customer);

    Customer findByNumber(@Param("number")String number);

    List<Customer> findByApplyNumber(@Param("applyNumber")String applyNumber);
}