package com.trw.bean;

/**
 * @Auther:tianrw
 * @Date: 2021/10/20  - 10:36
 * @Version:1.0
 * @Content: 部门表
 */
public class Dept {
    private int id;
    private String name;
    private String remark;

    public Dept() {
    }

    public Dept(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
