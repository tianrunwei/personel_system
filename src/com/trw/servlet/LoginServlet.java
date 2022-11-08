package com.trw.servlet;

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

/**
 * @Auther:tianrw
 * @Date: 2021/9/18  - 8:57
 * @Version:1.0
 * @Content:
 */
@WebServlet(urlPatterns = {"/LongServlet", "/register.action"})
public class LoginServlet extends HttpServlet {
    public LoginServlet() {
    }

    //创建service层对象
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建会话
        HttpSession session = req.getSession();
        //获取URI /hrm/ login
        String uri = req.getRequestURI();
        //字符串截取 subStrig /hrm/ login
        String action = uri.substring(uri.lastIndexOf("/") + 1);
//        System.out.println("action:" + action);
        //响应输出流
        PrintWriter pw = resp.getWriter();
        if (action.equals("LongServlet")) {
            //获取用户名和密码
            String username = req.getParameter("loginname");
            String password = req.getParameter("password");

            //不连接数据库，判断用户名和密码是否正确 admin 123
            User user = userService.login(username, password);
            session.setAttribute("user_inf", user);
            if (user != null) {
                //用户名和密码正确返回pw.print(1);
                pw.print(1);
            } else {
                //用户名和密码错误返回pw.print(0);
                pw.print(0);
            }
        } else if (action.equals("register.action")) {
            String loginname = req.getParameter("loginname");
            String password = req.getParameter("password");
            String username = req.getParameter("username");
            int i = userService.register(loginname, password, username);
            if (i > 0) {
                //注册成功
                pw.println(1);
            } else {
                pw.println(0);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
