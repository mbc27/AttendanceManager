package com.rabbiter.am.service;

import com.rabbiter.am.dao.CustomerDao;
import com.rabbiter.am.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public int deleteById(String id) {
        return customerDao.deleteById(id);
    }

    public int insert(Customer customer) {
        Customer customer1 = findByNumber(customer.getNumber());
        if(customer1 != null){
            return 1;
        }else {
            customer.setId(UUID.randomUUID().toString());
            customerDao.insert(customer);
            return 0;
        }
    }

    public Customer selectById(String id) {
        return customerDao.selectById(id);
    }

    public int update(Customer record) {
        return customerDao.update(record);
    }

    public List<Customer> getAll() {
        return customerDao.getAll();
    }

    public List<Customer> findByName(String name) {
        return customerDao.findByName(name);
    }

    public List<Customer> findByNameAndType(Customer customer){
        return customerDao.findByNameAndType(customer);
    }

    public Customer findByNumber(String number) {
        return customerDao.findByNumber(number);
    }

    public List<Customer> findByApplyNumber(String applyNumber) {
        return customerDao.findByApplyNumber(applyNumber);
    }
}
