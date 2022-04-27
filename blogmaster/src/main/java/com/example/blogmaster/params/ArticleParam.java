package com.example.blogmaster.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ivyevy
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleParam {

    private Long id;

    private String title;

    private String summary;

    private String category_name;

    private String content;

}
