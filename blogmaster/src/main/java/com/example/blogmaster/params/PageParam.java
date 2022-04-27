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
public class PageParam {

    private int page = 1;
    private int pageSize = 10;
}
