package com.example.blogmaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogmaster.aop.anno.Cache;
import com.example.blogmaster.mapper.BodyMapper;
import com.example.blogmaster.mapper.CategoryMapper;
import com.example.blogmaster.mapper.CommentMapper;
import com.example.blogmaster.params.ArticleParam;
import com.example.blogmaster.params.PageParam;
import com.example.blogmaster.pojo.Article;
import com.example.blogmaster.mapper.ArticleMapper;
import com.example.blogmaster.pojo.Body;
import com.example.blogmaster.pojo.Category;
import com.example.blogmaster.pojo.Comment;
import com.example.blogmaster.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogmaster.service.ICommentService;
import com.example.blogmaster.utis.AdminThreadLocal;
import com.example.blogmaster.utis.DateFormat;
import com.example.blogmaster.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ivyevy
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private BodyMapper bodyMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICommentService commentService;

    @Transactional
    @Override
    public Result publish(ArticleParam articleParam) {
        Article article = new Article();
        article.setAuthor_id(AdminThreadLocal.get().getId());
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setComment_count(0);
        article.setFavor(0L);
        article.setView_count(0);
        article.setCreate_date(System.currentTimeMillis());
        articleMapper.insert(article);

        Category category = new Category();
        category.setCategory_name(articleParam.getCategory_name());
        category.setArticle_id(article.getId());
        categoryMapper.insert(category);

        Body body = new Body();
        body.setContent(articleParam.getContent());
        body.setArticle_id(article.getId());
        bodyMapper.insert(body);

        article.setBody_id(body.getId());
        article.setCategory_id(category.getId());

        articleMapper.updateById(article);

        return Result.success(article);
    }

    @Cache(name = "首页文章列表")
    @Override
    public Result listArticle(PageParam pageParam){
        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        IPage<Article> articleVoIPage = articleMapper.listArticle(page);
        List<Article> records = articleVoIPage.getRecords();
        List<ArticleVo> articleVoList = toArticleVoList(records);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> toArticleVoList(List<Article> records){
        List<ArticleVo> articleVoList = new ArrayList<>();
        records.stream().forEach((Article article) -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVo.setCategory_name(article.getCategory().getCategory_name());
            articleVo.setUsername(article.getAdmin().getUsername());
            try {
                articleVo.setCreate_date(DateFormat.getDateFormatInstance(article.getCreate_date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            articleVoList.add(articleVo);
        });
        return articleVoList;
    }

    @Async("threadPool")
    @Override
    public Result articleFavor(Long id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, id);
        Article article = articleMapper.selectOne(queryWrapper);
        Long favor = article.getFavor();
        article.setFavor(favor+1);
        articleMapper.updateById(article);
        return Result.success("增加成功");
    }

    @Async("threadPool")
    public void updateViewCounts(Integer raw_view_count, Long article_id){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getView_count, raw_view_count)
                .eq(Article::getId, article_id);
        Article article = new Article();
        article.setView_count(raw_view_count+1);
        articleMapper.update(article, queryWrapper);
    }

    @Override
    public Result articleDetails(ArticleVoId articleVoId) {
        Long article_id = articleVoId.getArticle_id();
        Article article = articleMapper.selectArticleDetails(article_id);

        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        BeanUtils.copyProperties(article, articleDetailVo);
        articleDetailVo.setCategory_name(article.getCategory().getCategory_name());
        articleDetailVo.setUsername(article.getAdmin().getUsername());
        articleDetailVo.setContent(article.getBody().getContent());
        try {
            articleDetailVo.setCreate_date(DateFormat.getDateFormatInstance(article.getCreate_date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 更新浏览次数
        Integer raw_view_count = article.getView_count();
        updateViewCounts(raw_view_count, article_id);
        log.info("=============== 浏览数+1=============");

        Result r = commentService.commentsByArticleId(articleVoId);
        List<CommentVo> childComments = (List<CommentVo>) r.getData();
        articleDetailVo.setCommentVo(childComments);
        return Result.success(articleDetailVo);
    }

    @Cache(name = "最热文章列表")
    @Override
    public Result hotArticleList() {
        List<ArticleVo> articleVos = storeArticleList();
        return Result.success(articleVos);
    }

    @Cache(name = "最热标签列表")
    @Override
    public Result hotCategoryArticles() {
        List<ArticleVo> articleVos = storeArticleList();
        Map<String, List<ArticleVo>> collect = articleVos.stream().collect(Collectors.groupingBy(ArticleVo::getCategory_name, Collectors.toList()));
        return Result.success(collect);
    }

    @Cache(name = "文章归档")
    @Override
    public Result archive(){
        List<ArticleVo> articleVos = storeArticleList();
        for (ArticleVo articleVo : articleVos) {
            String create_date = articleVo.getCreate_date();
            String dataWithYearAndMonth = create_date.substring(0, 7);
            articleVo.setCreate_date(dataWithYearAndMonth);
        }
        Map<String, List<ArticleVo>> collect = articleVos.stream().collect(Collectors.groupingBy(ArticleVo::getCreate_date, Collectors.toList()));
        return Result.success(collect);
    }

    @Transactional
    @Override
    public Result updateArticle(ArticleParam articleParam) {
        Article article = new Article();
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getId, articleParam.getId());
        articleMapper.update(article, articleWrapper);

        Body body = new Body();
        body.setContent(articleParam.getContent());
        LambdaQueryWrapper<Body> bodyWarpper = new LambdaQueryWrapper<>();
        bodyWarpper.eq(Body::getArticle_id, articleParam.getId());
        bodyMapper.update(body, bodyWarpper);

        Category category = new Category();
        category.setCategory_name(articleParam.getCategory_name());
        LambdaQueryWrapper<Category> categoryWarpper = new LambdaQueryWrapper<>();
        categoryWarpper.eq(Category::getArticle_id, articleParam.getId());
        categoryMapper.update(category, categoryWarpper);
        return Result.success("更新成功");
    }

    @Override
    public Result deleteArticle(ArticleVoId articleVoId) {
        articleMapper.deleteArticle(articleVoId.getArticle_id());
        return Result.success("删除成功");
    }

    @Cache(name = "文章列表")
    public List<ArticleVo> storeArticleList(){
        List<Article> records = articleMapper.findArticleList();
        List<ArticleVo> articleVos = toArticleVoList(records);
        return articleVos;
    }
}


