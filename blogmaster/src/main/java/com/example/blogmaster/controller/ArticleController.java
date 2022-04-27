package com.example.blogmaster.controller;


import com.example.blogmaster.params.ArticleParam;
import com.example.blogmaster.params.PageParam;
import com.example.blogmaster.service.IArticleService;
import com.example.blogmaster.vo.ArticleVoId;
import com.example.blogmaster.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
@RestController
@Api(tags = "ArticleController")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @ApiOperation(value = "发布文章")
    @PostMapping
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    @ApiOperation(value = "查询首页文章")
    @PostMapping("/list")
    public Result listArticle(@RequestBody PageParam pageParam){
        return articleService.listArticle(pageParam);
    }

    @ApiOperation(value = "文章点赞")
    @PostMapping("/favor/{id}")
    public Result favor(@PathVariable("id") Long id){
        return articleService.articleFavor(id);
    }

    @ApiOperation(value = "查看文章详情")
    @PostMapping("/details")
    public Result articleDetails(@RequestBody ArticleVoId articleVoId){
        return articleService.articleDetails(articleVoId);
    }

    @ApiOperation(value = "查看最热文章")
    @PostMapping("/hot")
    public Result hotArticle(){
        return articleService.hotArticleList();
    }

    @ApiOperation(value = "文章归档")
    @GetMapping("Archive")
    public Result archive(){
        return articleService.archive();
    }

    @ApiOperation(value = "修改文章")
    @PutMapping
    public Result updateArticle(@RequestBody ArticleParam articleParam){
        return articleService.updateArticle(articleParam);
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping
    public Result deleteArticle(@RequestBody ArticleVoId articleVoId){
        return articleService.deleteArticle(articleVoId);
    }
}
