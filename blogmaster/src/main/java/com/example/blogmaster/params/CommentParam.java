package com.example.blogmaster.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Ivyevy
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentParam {

    private String content;

    private Long article_id;

    private Long comment_to;

    private Boolean is_father;

}
