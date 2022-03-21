package com.cloudisk.demo1.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cloudisk.demo1.entity.User;

public interface UserSerice {
  
    String changePass(User user,String newpassword);

    String registerUser(User user);

    String deleteUser(User user);

    String loginUser(HttpServletRequest request, HttpServletResponse response,@ModelAttribute User user);

    boolean checkUser(User user);
}
