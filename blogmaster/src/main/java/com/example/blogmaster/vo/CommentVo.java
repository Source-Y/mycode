package com.example.blogmaster.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivyevy
 * @version 1.0
 */
@Data
public class CommentVo implements Serializable {

    @ApiModelProperty(value = "评论内容")
    private String comment_content;

    @ApiModelProperty(value = "评论人")
    private String comment_username;

    @ApiModelProperty(value = "评论时间")
    private String comment_create_date;

    @ApiModelProperty(value = "评论点赞数")
    private Long comment_favor;

    private List<CommentVo> childComment;
}
