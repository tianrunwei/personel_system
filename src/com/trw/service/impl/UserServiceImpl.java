package com.trw.service.impl;

import com.trw.bean.User;
import com.trw.dao.UserDao;
import com.trw.dao.impl.UserDaoImpl;
import com.trw.service.UserService;

import java.util.List;

/**
 * @Auther:tianrw
 * @Date: 2021/9/18  - 9:01
 * @Version:1.0
 * @Content:
 */
public class UserServiceImpl implements UserService {

    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String loginname, String password) {
        return userDao.login(loginname, password);
    }

    @Override
    public int register(String loginname, String password, String username) {
        return userDao.register(loginname, password, username);
    }

    @Override
    public List<User> findAllUser(String status, String loginname, int page, int limit) {
        int statu1 = 0;
        //类型装换
        if (!"".equals(status) && status != null) {
            statu1 = Integer.parseInt(status);
        }
        return userDao.findAllUser(statu1, loginname, page, limit);
    }


    @Override
    public boolean checkName(String loginname) {
        List<User> name = userDao.checkName(loginname);
        if (name.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean delUser(int id) {
        return userDao.delUser(id);
    }

    @Override
    public boolean updUser(User user) {
        return userDao.updUser(user);
    }

    @Override
    public int count(String status, String loginname) {
        int statu1 = 0;
        //类型装换
        if (!"".equals(status) && status != null) {
            statu1 = Integer.parseInt(status);
        }
        return userDao.count(statu1,loginname);
    }

}
