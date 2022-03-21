package com.cloudisk.demo1.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cloudisk.demo1.entity.User;
import com.cloudisk.demo1.mapper.UserMapper;
import com.cloudisk.demo1.service.UserSerice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cloudisk.demo1.entity.CookieUtil;

@Service
public class UserServiceimpl implements UserSerice{
    @Resource
    private UserMapper usermapper;
   @Override
    public String changePass(User user, String newpassword) {
        
        return null;
    }

    @Override
    public String registerUser(User user) {
        try{
            usermapper.registerUser(user.getUsername() , user.getPassword());
        }catch(Exception e){
            System.out.println(e);
            return "unable to creat check input";
        }
        return "success";
    }

    @Override
    public String deleteUser(User user) {
        if(checkUser(user)){
            try{
                deleteUser(user);
                
            }catch(Exception e){
                System.out.println(e);
                return "User found fail to delete";
            }
            return "success,Data remove.";
        }else{
            return "no match date...";
        }
    }
    
    

    @Override
    public String loginUser(HttpServletRequest request, HttpServletResponse response,@ModelAttribute User user) {
        if(checkUser(user)){
            int expire = 60 * 60 * 24 * 7;
            CookieUtil.setCookie(request, response, "username", user.getUsername(), expire);
            CookieUtil.setCookie(request, response, "password", user.getPassword(), expire);
            
            return "succsee, logging you in...<a href="+"/"+">Check Here</a>";
        }else{
            return "no match date...<br><a href="+"/loginpage"+">Try Again</a><br><a href="+"/registerpage"+">SIGN UP</a>";
        }
    }

    @Override
    public boolean checkUser(User user) {
        boolean checkUser = false;
        // boolean checkUser = true;  //     不判断 不判断不判断不判断不判断不判断不判断不判断
        try{
            String apass = usermapper.getUserpassbyName(user.getUsername());
            String bpass = user.getPassword();
            if (apass!=null && apass.equals(bpass)){
                checkUser = true;
            }
            
        }catch(Exception e){
            System.out.println(e);
            return checkUser;
        }
        return checkUser;
    }
    
}