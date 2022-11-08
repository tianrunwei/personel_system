package com.trw.dao;

import com.trw.bean.User;

import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/9/18  - 9:03
 * @Version:1.0
 * @Content:
 */
public interface UserDao {
    User login(String loginname, String password);

    int register(String loginname, String password, String username);

    //查询所有用户
    List<User> findAllUser(int statu1, String loginname, int page, int limit);

    //模糊查询

    //检查登录名的方法
    List<User> checkName(String loginname);

    //添加用户
    boolean addUser(User user);

    //删除用户(根据id)
    boolean delUser(int id);

    //修改用户信息
    boolean updUser(User user);

    //查询总记录条数
    int count(int status, String loginname);
}
