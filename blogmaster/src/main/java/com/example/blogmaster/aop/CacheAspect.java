package com.example.blogmaster.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blogmaster.aop.anno.Cache;
import com.example.blogmaster.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Ivyevy
 * @version 1.0
 */

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.example.blogmaster.aop.anno.Cache)")
    public void pt(){}

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();

        log.info("==========================================================");
        log.info("==== {}类-->{}方法执行了", className, methodName);

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Object[] args = pjp.getArgs();
        String s = Arrays.toString(args);
        log.info("==== 参数：{}", s);

        Cache annotation = method.getAnnotation(Cache.class);

        long expire = annotation.expire();
        String name = annotation.name();
        log.info("==== 过期时间：{}", expire);
        log.info("==== 操作：{}", name);
        log.info("==========================================================");

        String keyName = className + "::" + methodName;
        String strResult = redisTemplate.opsForValue().get(keyName);

        if(StringUtils.isNotBlank(strResult)){
            log.info("==== {}类-->{}方法走了缓存", className, methodName);
            return JSON.parseObject(strResult, Result.class);
        }

        Object proceed = pjp.proceed();

        redisTemplate.opsForValue().set(keyName, JSON.toJSONString(proceed), expire, TimeUnit.SECONDS);
        log.info("==== {}类-->{}方法存入缓存", className, methodName);
        return proceed;
    }
}
