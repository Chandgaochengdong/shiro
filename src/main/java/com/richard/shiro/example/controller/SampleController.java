package com.richard.shiro.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author Richard
 * @Date 2019/9/17 21:22
 **/
@RestController
@RequestMapping("/sample")
public class SampleController {


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request, String username, String password) {
        System.out.println("  username : " +  username);
        System.out.println("  password : " +  password);
        System.out.println(" request query :" + request.getQueryString());
        System.out.println(" request : " + request.getPathInfo());

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);

        return "login sucess username ： " + username +  "   password : " + password;

    }

    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }




    @GetMapping("/read")
    @RequiresPermissions("document:read")
    public String read() {
        return " I am reading...";
    }


    @GetMapping("/write")
    @RequiresPermissions("document:write")
    public String write() {
        return " I am writing...";
    }
}
