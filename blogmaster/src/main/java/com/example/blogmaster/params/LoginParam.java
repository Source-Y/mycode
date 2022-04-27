package com.example.blogmaster.params;

import io.swagger.annotations.ApiModelProperty;
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
public class LoginParam {

    private String username;

    private String password;

    private String captcha;
}
