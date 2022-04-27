package com.example.blogmaster.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blogmaster.pojo.Admin;
import com.example.blogmaster.service.IAdminService;
import com.example.blogmaster.utis.AdminThreadLocal;
import com.example.blogmaster.utis.ErrorCode;
import com.example.blogmaster.utis.JwtUtils;
import com.example.blogmaster.vo.Result;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Ivyevy
 * @version 1.0
 */

/**
 * preHandle、postHandle与afterCompletion
 * preHandle
 *
 * 调用时间：Controller方法处理之前
 *
 * 执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序一个接一个执行
 *
 * 若返回false，则中断执行，注意：不会进入afterCompletion
 *
 *
 *
 * postHandle
 *
 * 调用前提：preHandle返回true
 *
 * 调用时间：Controller方法处理完之后，DispatcherServlet进行视图的渲染之前，也就是说在这个方法中你可以对ModelAndView进行操作
 *
 * 执行顺序：链式Intercepter情况下，Intercepter按照声明的顺序倒着执行。
 *
 * 备注：postHandle虽然post打头，但post、get方法都能处理
 *
 *
 *
 * afterCompletion
 *
 * 调用前提：preHandle返回true
 *
 * 调用时间：DispatcherServlet进行视图的渲染之后
 *
 * 多用于清理资源
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IAdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println(request.getHeaderNames());
        Claims claims = jwtUtils.parseToken(token);
        if (StringUtils.isBlank(token) || claims==null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        Date expiration = claims.getExpiration();
        if (expiration.before(new Date())){
            Result result = Result.fail(ErrorCode.SESSION_TIME_OUT.getCode(), ErrorCode.SESSION_TIME_OUT.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            System.out.println("走了");
            return false;
        }

        String username = claims.get("username").toString();
        Admin admin = adminService.findAdminByName(username);
        admin.setPassword(null);
        AdminThreadLocal.put(admin);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdminThreadLocal.remove();
    }
}
