package com.example.blogmaster.vo;

import com.example.blogmaster.pojo.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Ivyevy
 * @version 1.0
 */
@Data
public class ArticleDetailVo implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "评论数")
    private Integer comment_count;

    @ApiModelProperty(value = "浏览量")
    private Integer view_count;

    @ApiModelProperty(value = "创建时间")
    private String create_date;

    @ApiModelProperty(value = "点赞数")
    private Long favor;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "类别名称")
    private String category_name;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "评论及子评论")
    private List<CommentVo> commentVo;
}
