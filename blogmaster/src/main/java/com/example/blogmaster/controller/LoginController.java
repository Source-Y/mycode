package com.example.blogmaster.controller;

import com.example.blogmaster.params.LoginParam;
import com.example.blogmaster.service.IAdminService;
import com.example.blogmaster.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivyevy
 * @version 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录")
    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return adminService.login(loginParam);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/out")
    public Result logout(){
        return adminService.logout();
    }

    @ApiOperation(value = "注册")
    @PostMapping("register")
    public Result register(@RequestBody LoginParam loginParam, HttpServletRequest request){
        return adminService.register(loginParam, request);
    }
}
