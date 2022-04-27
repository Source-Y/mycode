package com.example.blogmaster.service.impl;

import com.example.blogmaster.pojo.Category;
import com.example.blogmaster.mapper.CategoryMapper;
import com.example.blogmaster.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ivyevy
 * @since 2022-04-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
