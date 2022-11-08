package com.trw.test;

import com.trw.bean.User;
import org.junit.Test;

import java.sql.*;

/**
 * @Auther:tianrw
 * @Date: 2021/9/18  - 9:19
 * @Version:1.0
 * @Content:
 */
public class Test_1 {

//
//    @Test
//    public void test() {
//
//        System.out.println("test junit");
//    }

    public static void main(String[] args) {
        String username = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/hrm?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM user_inf where ID=? AND `PASSWORD`=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setInt(2, 123456);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLoginname(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setStatus(rs.getInt(4));
                user.setCreateDate(rs.getDate(5));
                user.setUsername(rs.getString(6));
                System.out.println("数据为--》" + user);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
