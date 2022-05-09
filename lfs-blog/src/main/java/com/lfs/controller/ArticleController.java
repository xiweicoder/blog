package com.lfs.controller;

import com.lfs.domain.ResponseResult;
import com.lfs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //查询热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    //    查询对应分类下的文章 分类：java php等
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        ResponseResult result = articleService.articleList(pageNum, pageSize, categoryId);
        return result;
    }

//    点击阅读全文
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

}
