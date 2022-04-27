package com.example.blogmaster.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blogmaster.params.CommentParam;
import com.example.blogmaster.pojo.Comment;
import com.example.blogmaster.mapper.CommentMapper;
import com.example.blogmaster.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogmaster.utis.AdminThreadLocal;
import com.example.blogmaster.utis.DateFormat;
import com.example.blogmaster.vo.ArticleVoId;
import com.example.blogmaster.vo.CommentVo;
import com.example.blogmaster.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result publishComment(CommentParam commentParam) {
        Comment comment = new Comment();
        comment.setContent(commentParam.getContent());
        comment.setUsername(AdminThreadLocal.get().getUsername());
        comment.setIs_father(commentParam.getIs_father());
        comment.setArticle_id(commentParam.getArticle_id());
        comment.setFavor(0L);
        comment.setCreate_date(System.currentTimeMillis());
        if (comment.getIs_father()){
            comment.setComment_to(-1L);
        }
        comment.setComment_to(commentParam.getComment_to());
        commentMapper.insert(comment);
        return Result.success(comment);
    }

    @Override
    public Result commentsByArticleId(ArticleVoId articleVoId) {
        Long article_id = articleVoId.getArticle_id();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticle_id, article_id)
                    .orderByDesc(Comment::getFavor, Comment::getCreate_date);
        List<Comment> comments = commentMapper.selectList(queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(comments);

        return Result.success(commentVoList);
    }

    @Async("threadPool")
    @Override
    public Result commentFavor(Long commentId) {
        Comment comment = new Comment();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getId, commentId);
        Comment commentOne = commentMapper.selectOne(queryWrapper);
        comment.setFavor(commentOne.getFavor()+1);
        return Result.success("评论点赞");
    }

    private List<CommentVo> toCommentVoList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();

        for (Comment comment : comments) {
            if (comment.getIs_father()) {
                CommentVo commentVo = new CommentVo();
                commentVo.setId(comment.getId());
                commentVo.setComment_content(comment.getContent());
                commentVo.setComment_favor(comment.getFavor());
                commentVo.setComment_username(comment.getUsername());
                try {
                    commentVo.setComment_create_date(DateFormat.getDateFormatInstance(comment.getCreate_date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                commentVo.setChildComment(findCommentChild(comments, comment.getId()));
                commentVoList.add(commentVo);
            }
        }
        return commentVoList;
    }
    private List<CommentVo> findCommentChild(List<Comment> comments, Long currentId){
        List<CommentVo> childCommentVos = new ArrayList<>();
        List<Comment> childComments = comments.stream().filter(comment -> comment.getComment_to().equals(currentId)).collect(Collectors.toList());
        childComments.stream().forEach(comment -> {
            CommentVo commentVo = new CommentVo();
            commentVo.setComment_username(comment.getUsername());
            commentVo.setComment_content(comment.getContent());
            commentVo.setComment_favor(comment.getFavor());
            try {
                commentVo.setComment_create_date(DateFormat.getDateFormatInstance(comment.getCreate_date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            childCommentVos.add(commentVo);
        });
        return childCommentVos;
    }
}
