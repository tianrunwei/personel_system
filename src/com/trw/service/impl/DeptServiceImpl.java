package com.trw.service.impl;

import com.trw.bean.Dept;
import com.trw.dao.DeptDao;
import com.trw.dao.impl.DeptDaoImpl;
import com.trw.service.DeptService;

import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/10/20  - 10:41
 * @Version:1.0
 * @Content:
 */
public class DeptServiceImpl implements DeptService {
    DeptDao deptDao = new DeptDaoImpl();


    @Override
    public List<Dept> findAllDept(String name, int page, int limit) {
        return deptDao.findAllDept(name, page, limit);
    }

    @Override
    public int count(String name) {
        return deptDao.count(name);
    }

    @Override
    public boolean checkDept(String name) {
        List<Dept> depts = deptDao.checkDept(name);
        if (depts.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addDept(Dept dept) {
        return deptDao.addDept(dept);
    }

    @Override
    public boolean delDept(int id) {
        return deptDao.delDept(id);
    }

    @Override
    public boolean updDept(Dept dept) {
        return deptDao.updDept(dept);
    }
}
