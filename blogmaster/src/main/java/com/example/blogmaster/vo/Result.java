package com.example.blogmaster.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    /**
     * 统一返回值
     */
    private Boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Object data){
        return new Result(true, 200, "success", data);
    }

    public static Result fail(Integer code, String msg){
        return new Result(false, code, msg, null);
    }
}
