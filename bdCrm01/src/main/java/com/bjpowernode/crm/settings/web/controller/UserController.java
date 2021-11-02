package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.imp.UserServiceImpl;
import com.bjpowernode.crm.util.MD5Util;
import com.bjpowernode.crm.util.PrintJson;
import com.bjpowernode.crm.util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("用户进入到控制器");

        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)) {
            login(request, response);
        }
    }

    //在下面各个方法中我们创建某个Service对象，来提供用户所需的功能
    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("现在开始接收参数");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        System.out.println("ip======" + ip);

        //为什么要创建Service对象？因为在Controller中要调用Service层的方法，而调用方法前要先创建对
        // 象，方法要在接口中体现，方法的具体内容写在那个Service类的实现类中

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        //创建对象时考虑到动态代理，所以不是直接 =new UserService,我们通过代理类来增强功能
        try {
           User user = us.login(loginAct, loginPwd, ip);
           request.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(response,true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }


    }
}
