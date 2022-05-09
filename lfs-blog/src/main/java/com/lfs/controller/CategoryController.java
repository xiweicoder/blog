package com.lfs.controller;

import com.lfs.domain.ResponseResult;
import com.lfs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    页面显示分类列表
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
       return categoryService.getCategoryList();
    }
}
