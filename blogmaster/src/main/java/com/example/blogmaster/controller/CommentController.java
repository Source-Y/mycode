package com.example.blogmaster.controller;


import com.example.blogmaster.params.CommentParam;
import com.example.blogmaster.service.ICommentService;
import com.example.blogmaster.vo.ArticleVoId;
import com.example.blogmaster.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-23
 */
@RestController
@Api(tags = "CommentController")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @ApiOperation(value = "发表评论")
    @PostMapping
    public Result publishComment(@RequestBody CommentParam commentParam){
        return commentService.publishComment(commentParam);
    }

    @ApiOperation(value = "根据文章id查看评论")
    @PostMapping("/details")
    public Result commentsByArticleId(@RequestBody ArticleVoId articleVoId){
        return commentService.commentsByArticleId(articleVoId);
    }
}
