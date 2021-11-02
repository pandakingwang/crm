package com.bjpowernode.crm.settings.service.imp;

import com.bjpowernode.crm.exception.loginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.util.DateTimeUtil;
import com.bjpowernode.crm.util.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public User login(String loginAct, String loginPwd, String ip) throws loginException {

        Map<String,String> map = new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        map.put("ip",ip);
        User user = userDao.login(map);
        if (user == null){
            throw new loginException("账号密码错误");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0){
            throw new loginException("账号已失效");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new loginException("账号已锁定");
        }

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new loginException("Ip地址受限");
        }

        return user;
    }
}
