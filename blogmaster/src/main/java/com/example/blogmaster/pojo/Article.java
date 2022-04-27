package com.example.blogmaster.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_article")
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "评论数")
    private Integer comment_count;

    @ApiModelProperty(value = "浏览量")
    private Integer view_count;

    @ApiModelProperty(value = "作者id")
    private Long author_id;

    @ApiModelProperty(value = "类目id")
    private Long category_id;

    @ApiModelProperty(value = "创建时间")
    private Long create_date;

    @ApiModelProperty(value = "点赞数")
    private Long favor;

    @ApiModelProperty(value = "文章主体id")
    private Long body_id;

    @TableField(exist = false)
    private Body body;

    @TableField(exist = false)
    private Admin admin;

    @TableField(exist = false)
    private Category category;
}
