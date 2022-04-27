package com.example.blogmaster.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Ivyevy
 * @version 1.0
 */
@Data
public class ArticleVo implements Serializable{

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "评论数")
    private Integer comment_count;

    @ApiModelProperty(value = "浏览量")
    private Integer view_count;

    @ApiModelProperty(value = "创建时间")
    private String create_date;

    @ApiModelProperty(value = "点赞数")
    private Long favor;

    @ApiModelProperty(value = "类别名称")
    private String category_name;

    @ApiModelProperty(value = "用户名")
    private String username;
}
