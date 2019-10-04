package com.richard.shiro.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
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
       // System.out.println(" request : " + request.getPathInfo());

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("登录 用户帐号或密码不正确");
            return null;
        } catch (LockedAccountException lae) {
            System.out.println("登录 用户帐号已锁定不可用");
            return null;

        } catch (AuthenticationException ae) {
            System.out.println("登录 认证失败");
            return null;
        }

        return "login sucess username ： " + username +  "   password : " + password;

    }

    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }




    @GetMapping("/read")
    @RequiresPermissions("sample:read")
    public String read() {
        return " I am reading...";
    }


    @GetMapping("/write")
    @RequiresPermissions("sample:write")
    public String write() {
        return " I am writing...";
    }
}
