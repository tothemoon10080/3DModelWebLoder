package com.cloudisk.demo1.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudisk.demo1.service.CheckCookie;

@Controller
public class HomeController {
    @Resource
    private CheckCookie checkCookie;
   
    @RequestMapping("/")
    public String mainPage(HttpServletRequest request){
        if( checkCookie.Check(request)){
            return "demo";
        }else{
            return "Login";
        }
    }
    @RequestMapping("/loginpage")
    public String loginpage(){
        return "Login";
    }
    @RequestMapping("/registerpage")
    public String registerpage(){
        return "/register";
    }
    @RequestMapping("/settingpage")
    public String settingpage(){
        return "setting";
    }
    @RequestMapping("/filemenu")
    public String filemenu(){
        return "filemenu";
    }
    
}
