package com.example.blogmaster.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blogmaster.params.LoginParam;
import com.example.blogmaster.pojo.Admin;
import com.example.blogmaster.mapper.AdminMapper;
import com.example.blogmaster.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogmaster.utis.AdminThreadLocal;
import com.example.blogmaster.utis.ErrorCode;
import com.example.blogmaster.utis.JwtUtils;
import com.example.blogmaster.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private JwtUtils jwtUtils;
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String encodePwd = DigestUtils.md5Hex(password + secret);
        System.out.println(encodePwd);

        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username)
                .eq(Admin::getPassword, encodePwd);
        Admin admin = adminMapper.selectOne(queryWrapper);

        if (admin == null){
            return Result.fail(ErrorCode.NO_ADMIN.getCode(), ErrorCode.NO_ADMIN.getMsg());
        }

        if (admin.getEnabled()==false){
            return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }
        String token = jwtUtils.createToken(username);

        return Result.success(token);
    }

    @Override
    public Admin findAdminByName(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public Result logout() {
        //由前端清除token
        AdminThreadLocal.remove();
        return Result.success("登出");
    }

    @Transactional
    @Override
    public Result register(LoginParam loginParam, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        String code = loginParam.getCaptcha();
        if(StringUtils.isBlank(code)||!captcha.equalsIgnoreCase(code)){
            return Result.fail(ErrorCode.CAPTCHA_ERROR.getCode(), ErrorCode.CAPTCHA_ERROR.getMsg());
        }
        Admin admin = new Admin();
        admin.setEnabled(true);
        admin.setUsername(loginParam.getUsername());
        admin.setPassword(DigestUtils.md5Hex(loginParam.getPassword() + secret));
        adminMapper.insert(admin);
        return Result.success("登录成功");
    }
}
