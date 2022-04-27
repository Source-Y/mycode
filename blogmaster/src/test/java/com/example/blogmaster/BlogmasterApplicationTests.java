package com.example.blogmaster;

import com.example.blogmaster.service.IArticleService;
import com.example.blogmaster.vo.ArticleVoId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@SpringBootTest
class BlogmasterApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    void contextLoads() {
        Set<String> keys = redisTemplate.keys("*");
        System.out.println(keys);
    }
}
