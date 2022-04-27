package com.example.blogmaster.aop.anno;

import java.lang.annotation.*;

/**
 * @author Ivyevy
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cache {
    long expire() default 1*60*5;
    String name() default "";
}
