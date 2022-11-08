package com.trw.servlet;

import com.google.gson.Gson;
import com.trw.bean.Dept;
import com.trw.service.DeptService;
import com.trw.service.UserService;
import com.trw.service.impl.DeptServiceImpl;
import com.trw.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:tianrw
 * @Date: 2021/10/20  - 10:42
 * @Version:1.0
 * @Content:
 */
@WebServlet(urlPatterns = {"/addDept.action", "/deptList.action", "/delDept.action",
        "/delDepts.action", "/checkDeptName.action", "/updDept.action"})
public class DeptServlet extends HttpServlet {
    DeptService deptService = new DeptServiceImpl();
    private PrintWriter pw = null;

    //gson转换器
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String uri = req.getRequestURI();
        //字符串截取 subStrig /hrm/ login
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        //打印输出流
        pw = resp.getWriter();
        switch (action) {
            case "deptList.action":
                deptList(req, resp);
                break;
            case "delDept.action":
                delDept(req, resp);
                break;
            case "delDepts.action":
                delDepts(req, resp);
                break;
            case "checkDeptName.action":
                checkDeptName(req, resp);
                break;
            case "addDept.action":
                addDept(req, resp);
                break;
            case "updDept.action":
                updDept(req, resp);
                break;
            default:
                break;
        }
    }

    /**
     * 修改部门信息
     *
     * @param req
     * @param resp
     */
    private void updDept(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String remark = req.getParameter("remark");
        Dept dept = new Dept();
        dept.setId(Integer.parseInt(id));
        dept.setName(name);
        dept.setRemark(remark);
        boolean b = deptService.updDept(dept);
        if (b) {
            //成功
            pw.print(1);
        } else {
            pw.print(0);
        }
    }

    /**
     * 添加部门
     *
     * @param req
     * @param resp
     */
    private void addDept(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String remark = req.getParameter("remark");
        Dept dept = new Dept(name, remark);
        boolean b = deptService.addDept(dept);
        if (b) {
            //成功
            pw.print(1);
        } else {
            pw.print(0);
        }
    }

    /**
     * 检查部门名称是否存在
     *
     * @param req
     * @param resp
     */
    private void checkDeptName(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        boolean b = deptService.checkDept(name);
        if (b) {
            //存在
            pw.print(0);
        } else {
            pw.print(1);
        }

    }

    /**
     * 批量删除
     *
     * @param req
     * @param resp
     */
    private void delDepts(HttpServletRequest req, HttpServletResponse resp) {

        String ids = req.getParameter("ids");

        boolean flag = false;
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            flag = deptService.delDept(Integer.parseInt(split[i]));
        }
        if (flag) {
            //成功
            pw.print(1);
        } else {
            pw.print(0);
        }
    }

    /**
     * 删除单个
     *
     * @param req
     * @param resp
     */
    private void delDept(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean b = deptService.delDept(id);
        if (b) {
            //成功删除
            pw.print(1);
        } else {
            pw.print(0);
        }

    }

    /**
     * 展示全部部门
     *
     * @param req
     * @param resp
     */
    private void deptList(HttpServletRequest req, HttpServletResponse resp) {

        //每页展示多少条数据
        int limit = Integer.parseInt(req.getParameter("limit"));
        //当前的页码
        int page = Integer.parseInt(req.getParameter("page"));

        String name = req.getParameter("name");

        List<Dept> depts = deptService.findAllDept(name, page, limit);
        int count = deptService.count(name);
        Map<Object, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", depts);
        map.put("count", count);//总记录条数
        //将map 装为json
        pw.print(gson.toJson(map));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
