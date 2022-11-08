package com.trw.service;

import com.trw.bean.Dept;

import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/10/20  - 10:41
 * @Version:1.0
 * @Content:
 */
public interface DeptService {
    //查询所有部门
    List<Dept> findAllDept(String name, int page, int limit);

    //查询总记录条数
    int count(String name);

    //检查部门是否存在
    boolean checkDept(String name);

    //添加部门
    boolean addDept(Dept dept);

    //删除部门
    boolean delDept(int id);

    //修改部门信息
    boolean updDept(Dept dept);
}
