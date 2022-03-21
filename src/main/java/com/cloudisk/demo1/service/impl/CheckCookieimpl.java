package com.cloudisk.demo1.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.cloudisk.demo1.entity.CookieUtil;
import com.cloudisk.demo1.entity.User;

import com.cloudisk.demo1.service.*;

import org.springframework.stereotype.Service;

@Service
public class CheckCookieimpl implements CheckCookie{

    @Resource
    private UserSerice userService;
    

    @Override
    public boolean Check(HttpServletRequest request) {
        boolean CheckCookie =false; //不判断 不判断不判断不判断不判断不判断
       
        Map<String, String> map = CookieUtil.getCookies(request);
        String username = map.get("username");
        String password = map.get("password");                       
        if(username != null && password != null){ 
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);          
            if(userService.checkUser(user)){
                CheckCookie = true;
            }
        }
        return CheckCookie;
    }
    
}
