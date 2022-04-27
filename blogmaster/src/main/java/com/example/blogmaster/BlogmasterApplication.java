package com.example.blogmaster;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.blogmaster.mapper")
public class BlogmasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogmasterApplication.class, args);
    }

}
