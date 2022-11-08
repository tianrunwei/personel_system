package com.trw.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/9/28  - 9:38
 * @Version:1.0
 * @Content: T使用的时候类型就要确定下来
 */
public abstract class JDBCUtils<T> {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    //静态代码块只会在类加载时初始化一遍
    private static final String ROOT = "root";
    private static final String PWD = "123456";
    private static final String URL = "jdbc:mysql://localhost:3306/hrm?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";

    static {
        //1加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //2.获取连接 url root password
    public Connection getCon() {
        try {
            con = DriverManager.getConnection(URL, ROOT, PWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //统一查询<T> ...表示可变参数本质为数组
    public List<T> query(String sql, Object... obj) {
        List<T> list = new ArrayList<>();
        try {
            //预编译SQL语句
            ps = getCon().prepareStatement(sql);
            //给？赋值
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            //执行SQL语句返回结果集
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getBean(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, con, ps);
        }

        return null;
    }

    /**
     * 统一更新
     *
     * @param sql
     * @param obj
     * @return 返回true表示更新成功
     */
    public boolean update(String sql, Object... obj) {
        try { //预编译SQL语句
            ps = getCon().prepareStatement(sql);

            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null, con, ps);
        }
        return false;
    }

    //查询总记录条数
    public int count(String sql) {
        try {
            //预编译SQL语句
            ps = getCon().prepareStatement(sql);

            //执行SQL语句返回结果集
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs, con, ps);
        }
        return 0;
    }


    //定义实体类抽象方法
    public abstract T getBean(ResultSet rs);

    //关闭资源
    public void close(ResultSet rs, Connection con, PreparedStatement ps) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
