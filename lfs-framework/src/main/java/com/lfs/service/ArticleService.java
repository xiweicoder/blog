package com.lfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    //查询热门文章
    ResponseResult hotArticleList();

    //查询对应分类下的文章
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    //    点击阅读全文
    ResponseResult getArticleDetail(Long id);

    //    访问博客查询到redis中的浏览量
    ResponseResult updateViewCount(Long id);
}
