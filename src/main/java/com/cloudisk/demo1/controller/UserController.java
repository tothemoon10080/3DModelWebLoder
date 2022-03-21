package com.cloudisk.demo1.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudisk.demo1.entity.CookieUtil;
import com.cloudisk.demo1.entity.User;
import com.cloudisk.demo1.service.UserSerice;

@RestController
public class UserController {
    @Resource
    private UserSerice userservice;

    @RequestMapping("/loginUser")
    String login(HttpServletRequest request, HttpServletResponse response,@ModelAttribute User user) {
        return userservice.loginUser(request,response,user);
    }    
    @RequestMapping("/registerUser")
    String register(@ModelAttribute User user){
        return userservice.registerUser(user);
    }
    @RequestMapping("deleteUser")
    String deleteUser(@ModelAttribute User user){
        return userservice.deleteUser(user);
    }
    @RequestMapping("changepass")
    String changepass(@ModelAttribute User user , @RequestParam String newpassword){
        return userservice.changePass(user, newpassword);
    }
    @RequestMapping("/logout")
    String logout(HttpServletRequest request,HttpServletResponse response){
        CookieUtil.clear(request, response);
        return "<a href="+"/"+">返回主頁</a>";
    }
    
}
