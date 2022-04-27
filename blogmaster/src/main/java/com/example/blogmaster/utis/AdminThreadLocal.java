package com.example.blogmaster.utis;

import com.example.blogmaster.pojo.Admin;

/**
 * @author Ivyevy
 * @version 1.0
 */
public class AdminThreadLocal {

    private AdminThreadLocal(){}

    private static final ThreadLocal<Admin> LOCAL = new ThreadLocal<>();

    public static void put(Admin admin){
        LOCAL.set(admin);
    }
    public static Admin get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
