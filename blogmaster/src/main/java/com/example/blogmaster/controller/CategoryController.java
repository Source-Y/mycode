package com.example.blogmaster.controller;


import com.example.blogmaster.service.IArticleService;
import com.example.blogmaster.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private IArticleService articleService;

    @ApiOperation(value = "查看最热标签的文章列表")
    @PostMapping("hotCategoryArticles")
    public Result hotCategoryArticles(){
        return articleService.hotCategoryArticles();
    }
}
