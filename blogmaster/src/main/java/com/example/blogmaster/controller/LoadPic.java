package com.example.blogmaster.controller;

import com.example.blogmaster.vo.Result;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.io.File;

/**
 * @author Ivyevy
 * @version 1.0
 */
@RestController
public class LoadPic {
    @PostMapping("/upload")
    public Object upload(MultipartFile fileUpload){
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片，写到需要保存的目录下
        try {
            String filePath = ResourceUtils.getURL("static").getPath();
            System.out.println(filePath);
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath+fileName));
            //返回提示信息
            return Result.success("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(-1,"上传失败");
        }
    }

}
