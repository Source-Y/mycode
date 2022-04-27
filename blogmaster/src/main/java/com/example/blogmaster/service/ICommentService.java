package com.example.blogmaster.service;

import com.example.blogmaster.params.CommentParam;
import com.example.blogmaster.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogmaster.vo.ArticleVoId;
import com.example.blogmaster.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-23
 */
public interface ICommentService extends IService<Comment> {

    Result publishComment(CommentParam commentParam);

    Result commentsByArticleId(ArticleVoId articleVoId);
}
