package com.lfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-05-07 09:36:06
 */
public interface CategoryService extends IService<Category> {

//    页面显示分类列表
    ResponseResult getCategoryList();
}

