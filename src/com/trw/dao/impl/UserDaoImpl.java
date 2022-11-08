package com.trw.dao.impl;

import com.trw.bean.User;
import com.trw.dao.UserDao;
import com.trw.utils.JDBCUtils;

import java.sql.*;
import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/9/18  - 9:04
 * @Version:1.0
 * @Content:
 */
public class UserDaoImpl extends JDBCUtils<User> implements UserDao {
    @Override
    public User login(String loginname, String password) {
        String sql = "SELECT * FROM user_inf where loginname=? AND `PASSWORD`=?";

        List<User> user = query(sql, loginname, password);
        if (user.size() > 0) {
            return user.get(0);
        }
        return null;
    }

    @Override
    public int register(String loginname, String password, String username) {
        //1加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //2.获取连接 url root password
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrm?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC", "root", "123456");
            //3定义SQL语句
            String sql = "INSERT INTO user_inf VALUES(NULL,?,?,2,CURRENT_TIMESTAMP,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, loginname);
            ps.setString(2, password);
            ps.setString(3, username);
            //4执行SQL语句
            int i = ps.executeUpdate();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<User> findAllUser(int statu1, String loginname, int page, int limit) {
        String sql = "SELECT * FROM user_inf WHERE 1=1";
        //判断登录名和状态是否为空
        if (loginname != null && !"".equals(loginname)) {
            sql += " and loginname like'%" + loginname + "%'";
        }
        if (statu1 != 0) {
            sql += " and status =" + statu1 + "";
        }
        sql += " limit ?,?";
        return query(sql, (page - 1) * limit, limit);
    }


    @Override
    public List<User> checkName(String loginname) {

        return query("select * from user_inf where loginname=?", loginname);
    }

    @Override
    public boolean addUser(User user) {

        return update("insert into user_inf values(null,?,?,?,CURRENT_TIMESTAMP,?)", user.getLoginname()
                , user.getPassword(), user.getStatus(), user.getUsername());
    }

    @Override
    public boolean delUser(int id) {

        return update("delete from user_inf where id=?", id);
    }

    @Override
    public boolean updUser(User user) {
        return update("update user_inf set loginname=? ,status=?, username=? where id=?", user.getLoginname(), user.getStatus(), user.getUsername(), user.getId());
    }

    @Override
    public int count(int status, String loginname) {
        String sql = "SELECT count(id) FROM user_inf WHERE 1=1";
        //判断登录名和状态是否为空
        if (loginname != null && !"".equals(loginname)) {
            sql += " and loginname like'%" + loginname + "%'";
        }
        if (status != 0) {
            sql += " and status =" + status + "";
        }

        return count(sql);
    }

    @Override
    public User getBean(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt(1));
            user.setLoginname(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setStatus(rs.getInt(4));
            user.setCreateDate(rs.getDate(5));
            user.setUsername(rs.getString(6));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
