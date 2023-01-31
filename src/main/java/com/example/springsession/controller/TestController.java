package com.example.springsession.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author:letg(pz)
 * @Date: 2023/1/31 15:58
 * @Description:
 */

@RestController
public class TestController {

  @Autowired
  private SessionRepository repository;
  @RequestMapping("/test1")
  public Object test1(HttpServletRequest request){
    HttpSession session = request.getSession();
    session.setAttribute("test","123");
    return 1;
  }
  @RequestMapping("/test2")
  public Object test2(HttpServletRequest request){
    HttpSession session = request.getSession();
    return session.getAttribute("test");

  }
}
