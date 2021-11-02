package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.exception.loginException;
import com.bjpowernode.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws loginException;
}
