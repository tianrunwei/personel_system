package com.trw.bean;

import java.util.Date;

/**
 * @Auther:tianrw
 * @Date: 2021/9/15  - 11:50
 * @Version:1.0
 * @Content:
 */
public class User  {
    private int id;
    private int status;
    private Date createDate;
    private String loginname;//登录名
    private String password;
    private String username;//用户名

    public User() {
    }

    public User(int status, String loginname, String password, String username) {
        this.status = status;
        this.loginname = loginname;
        this.password = password;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", status=" + status +
                ", createDate=" + createDate +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
