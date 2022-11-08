package com.trw.filter;

import com.trw.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther:tianrw
 * @Date: 2021/9/27  - 9:14
 * @Version:1.0
 * @Content:
 */
@WebFilter({"*.action", "*.html", "*.jsp"})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
//        System.out.println(uri);
//        System.out.println("/hrm/jsp/login.html".equals(uri));
        if ("/hrm/jsp/login.html".equals(uri) ||
                uri.equals("/hrm/login.action") || uri.equals("/hrm/register.html")) {
//            System.out.println("123");
            filterChain.doFilter(request, response);

        } else {

            //获取会话
            HttpSession session = request.getSession();
            User userInf = (User) session.getAttribute("user_inf");
            if (userInf != null) {
                filterChain.doFilter(request, response);
            } else {
                //为空返回登录页面
                //重定向,路径会改变,转发不会
                response.sendRedirect("/hrm/jsp/login.html");
            }

        }
    }

    @Override
    public void destroy() {

    }
}
