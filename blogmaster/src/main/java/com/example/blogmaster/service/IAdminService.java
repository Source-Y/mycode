package com.example.blogmaster.service;

import com.example.blogmaster.params.LoginParam;
import com.example.blogmaster.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogmaster.vo.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
public interface IAdminService extends IService<Admin> {

    Result login(LoginParam loginParam);

    Admin findAdminByName(String username);

    Result logout();

    Result register(LoginParam loginParam, HttpServletRequest request);
}
