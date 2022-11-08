package com.trw.service;

import com.trw.bean.User;

import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/9/18  - 8:59
 * @Version:1.0
 * @Content:
 */
public interface UserService {
    User login(String loginname, String password);

    int register(String loginname, String password, String username);

    //查询所有用户
    List<User> findAllUser(String status, String loginname, int page, int limit);

    /**
     * 判断用户是否存在
     *
     * @param loginname
     * @return 存在用户返回true
     */
    boolean checkName(String loginname);

    //添加用户
    boolean addUser(User user);
    //删除用户(根据id)
    boolean delUser(int id);

    boolean updUser(User user);
    //查询总记录条数
    int count(String status, String loginname);
}
