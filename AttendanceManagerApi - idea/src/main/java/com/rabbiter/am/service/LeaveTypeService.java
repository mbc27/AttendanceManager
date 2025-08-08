package com.rabbiter.am.service;

import com.rabbiter.am.dao.LeaveTypeDao;
import com.rabbiter.am.entity.LeaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeDao leaveTypeDao;

    public int deleteById(String id) {
        return leaveTypeDao.deleteById(id);
    }

    public int insert(LeaveType leaveType) {
        return leaveTypeDao.insert(leaveType);
    }

    public LeaveType selectById(String id) {
        return leaveTypeDao.selectById(id);
    }

    public int update(LeaveType leaveType) {
        return leaveTypeDao.update(leaveType);
    }

    public List<LeaveType> getAll() {
        return leaveTypeDao.getAll();
    }
}
