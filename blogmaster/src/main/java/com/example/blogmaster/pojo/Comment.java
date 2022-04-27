package com.example.blogmaster.pojo;

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
 * @since 2022-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_comment")
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "文章id")
    private Long article_id;

    @ApiModelProperty(value = "评论人")
    private String username;

    @ApiModelProperty(value = "给谁评论")
    private Long comment_to;

    @ApiModelProperty(value = "评论时间")
    private Long create_date;

    @ApiModelProperty(value = "是否是父评论")
    private Boolean is_father;

    @ApiModelProperty(value = "评论点赞数")
    private Long favor;


}
