package com.trw.dao.impl;

import com.trw.bean.Dept;
import com.trw.dao.DeptDao;
import com.trw.utils.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/10/20  - 10:40
 * @Version:1.0
 * @Content:
 */
public class DeptDaoImpl extends JDBCUtils<Dept> implements DeptDao {
    @Override
    public List<Dept> findAllDept(String name, int page, int limit) {

        String sql = "select * from dept_inf where 1=1";

        //判断部门是否为空
        if (name != null && !"".equals(name)) {
            sql += " and NAME like'%" + name + "%'";
        }
        sql += " limit ?,?";

        return query(sql, (page - 1) * limit, limit);
    }

    @Override
    public int count(String name) {
        String sql = "select count(ID) from dept_inf where 1=1";

        if (name != null && !"".equals(name)) {
            sql += " and NAME like'%" + name + "%'";
        }

        //同名使用父类的方法
        return super.count(sql);
    }

    @Override
    public List<Dept> checkDept(String name) {
        return query("select * from dept_inf where NAME=?", name);
    }

    @Override
    public boolean addDept(Dept dept) {
        return update("insert into dept_inf values(null,?,?)", dept.getName(), dept.getRemark());
    }

    @Override
    public boolean delDept(int id) {
        return update("delete from dept_inf where ID=?", id);
    }

    @Override
    public boolean updDept(Dept dept) {
        return update("update dept_inf set NAME=? ,REMARK=? where ID=?", dept.getName(), dept.getRemark(), dept.getId());
    }

    @Override
    public Dept getBean(ResultSet rs) {
        Dept dept = new Dept();
        try {
            dept.setId(rs.getInt(1));
            dept.setName(rs.getString(2));
            dept.setRemark(rs.getString(3));
            return dept;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
