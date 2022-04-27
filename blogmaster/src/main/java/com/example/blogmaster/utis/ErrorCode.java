package com.example.blogmaster.utis;

/**
 * @author Ivyevy
 * @version 1.0
 */
public enum  ErrorCode {

    CAPTCHA_ERROR(10001,"验证码错误，请重新输入"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    NO_PERMISSION(10003,"无访问权限"),
    SESSION_TIME_OUT(10004,"会话超时"),
    NO_LOGIN(10005,"未登录"),
    NO_ADMIN(10006, "用户不存在"),
    SYSTEM_ERROR(10007, "系统异常");

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

