package com.rabbiter.am.service;

import com.rabbiter.am.dao.FixedassetTypeDao;
import com.rabbiter.am.entity.FixedassetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FixedassetTypeService {

    @Autowired
    private FixedassetTypeDao fixedassetTypeDao;

    public int deleteById(String id) {
        return fixedassetTypeDao.deleteById(id);
    }

    public int insert(FixedassetType fixedassetType) {
        fixedassetType.setId(UUID.randomUUID().toString());
        fixedassetType.setQuantity(0);
        fixedassetTypeDao.insert(fixedassetType);
        return 0;
    }

    public FixedassetType selectById(String id) {
        return fixedassetTypeDao.selectById(id);
    }

    public int update(FixedassetType fixedassetType) {
        return fixedassetTypeDao.update(fixedassetType);
    }

    public List<FixedassetType> getAll() {
        return fixedassetTypeDao.getAll();
    }
}
