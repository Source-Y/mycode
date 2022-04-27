package com.example.blogmaster.service;

import com.example.blogmaster.params.ArticleParam;
import com.example.blogmaster.params.PageParam;
import com.example.blogmaster.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogmaster.vo.ArticleVoId;
import com.example.blogmaster.vo.Result;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
public interface IArticleService extends IService<Article> {

    Result publish(ArticleParam articleParam);

    Result listArticle(PageParam pageParam);

    Result articleFavor(Long id);

    Result articleDetails(ArticleVoId articleVoId);

    Result hotArticleList();

    Result hotCategoryArticles();

    Result archive();

    Result updateArticle(ArticleParam articleParam);

    Result deleteArticle(ArticleVoId articleVoId);
}
