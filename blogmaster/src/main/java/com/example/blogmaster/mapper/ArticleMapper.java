package com.example.blogmaster.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogmaster.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blogmaster.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> listArticle(@Param("page") Page<Article> page);

    Article selectArticleDetails(@Param("article_id")Long article_id);

    List<Article> findHotArticles();

    List<Article> findArticleList();

    void deleteArticle(@Param("id") Long id);
}
