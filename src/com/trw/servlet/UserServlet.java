package com.trw.servlet;

import com.google.gson.Gson;
import com.trw.bean.User;
import com.trw.service.UserService;
import com.trw.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:tianrw
 * @Date: 2021/9/26  - 10:42
 * @Version:1.0
 * @Content:
 */
@WebServlet(urlPatterns = {"/userlist.action",
        "/checkName.action", "/addUser.action", "/delUser.action", "/delUsers.action", "/updUser.action"})
public class UserServlet extends HttpServlet {
    public UserService userService = new UserServiceImpl();
    private PrintWriter pw = null;

    //gson转换器
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        String uri = req.getRequestURI();
        //字符串截取 subStrig /hrm/ login
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        //打印输出流
        pw = resp.getWriter();
        switch (action) {
            case "userlist.action":
                userList(req, resp);
                break;
            case "delUsers.action":
                delUsers(req, resp);
                break;
            case "checkName.action":
                checkName(req, resp);
                break;
            case "addUser.action":
                addUser(req, resp);
                break;
            case "delUser.action":
                delUser(req, resp);
            case "updUser.action":
                updUser(req, resp);
                break;
            default:
                break;
        }

    }

    /**
     * 修改用户信息
     *
     * @param req
     * @param resp
     */
    private void updUser(HttpServletRequest req, HttpServletResponse resp) {
        //获取id
        int id = Integer.parseInt(req.getParameter("id"));//修改的用户id
        String loginname = req.getParameter("loginname");
        String username = req.getParameter("username");
        int status = Integer.parseInt(req.getParameter("status"));//用户的状态
        User user = new User();
        user.setId(id);
        user.setLoginname(loginname);
        user.setUsername(username);
        user.setStatus(status);
        boolean flag = userService.updUser(user);
        if (flag) {
            //修改成功
            pw.print(1);
        } else {
            pw.print(2);
        }

    }

    /**
     * 批量删除用户
     *
     * @param req
     * @param resp
     */
    private void delUsers(HttpServletRequest req, HttpServletResponse resp) {
        //获取多个id
        String ids = req.getParameter("ids");
        boolean flag = false;
        boolean flag2 = true;
        //将多个id提取出来 split
        String[] split = ids.split(",");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user_inf");
        for (int i = 0; i < split.length; i++) {
            if (Integer.parseInt(split[i]) == user.getId()) {
                pw.print(user.getId());
                flag2 = false;
            }
        }
        if (flag2) {
            //删除
            for (int i = 0; i < split.length; i++) {
                //循环删除
                flag = userService.delUser(Integer.parseInt(split[i]));
            }
            if (flag) {
                //删除成功
                pw.print(-1);
            } else {//删除成功
                pw.print(0);
            }
        }
    }


    /**
     * 新增用户
     *
     * @param req
     * @param resp
     */
    private void addUser(HttpServletRequest req, HttpServletResponse resp) {

        String loginname = req.getParameter("loginname");
        String username = req.getParameter("username");
        String status = req.getParameter("status");
        String password = req.getParameter("password");
        User user = new User(Integer.parseInt(status), loginname, password, username);

        boolean b = userService.addUser(user);
        if (b) {
            //true添加成功
            pw.print(1);
        } else {
            pw.print(2);
        }

    }

    /**
     * 检查用户名是否存在
     *
     * @param req
     * @param resp
     */
    private void checkName(HttpServletRequest req, HttpServletResponse resp) {
        //获取登录名
        String loginname = req.getParameter("loginname");
        //调用service
        boolean flag = userService.checkName(loginname);
        if (flag) {
            //true登录名已经存在
            pw.print(0);
        } else {
            //false表示用户不存在
            pw.print(1);
        }

    }

    /**
     * 根据主键id删除用户
     *
     * @param req
     * @param resp
     */
    private void delUser(HttpServletRequest req, HttpServletResponse resp) {
        //使用int包装类转换
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user_inf");
        if (user.getId() == id) {
            pw.print(id);
        } else {
            boolean b = userService.delUser(id);
            if (b) {
                pw.print(-1);
            } else {
                pw.print(0);
            }
        }
    }

    /**
     * 查询所有用户
     *
     * @param req
     * @param resp
     */
    private void userList(HttpServletRequest req, HttpServletResponse resp) {
        //每页展示多少条数据
        int limit = Integer.parseInt(req.getParameter("limit"));
        //当前的页码
        int page = Integer.parseInt(req.getParameter("page"));

        String status = req.getParameter("status");
        String loginname = req.getParameter("username");
        List<User> users = userService.findAllUser(status, loginname, page, limit);
        int count = userService.count(status, loginname);
//        List<User> users = userService.findAllUser();
        Map<Object, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", users);
        map.put("count", count);//总记录条数
        //将map 装为json

        pw.print(gson.toJson(map));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //post方法是先进post在进里面的get
        doGet(req, resp);
    }
}
