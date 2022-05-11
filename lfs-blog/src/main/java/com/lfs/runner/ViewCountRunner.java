package com.lfs.runner;

import com.lfs.domain.entity.Article;
import com.lfs.mapper.ArticleMapper;
import com.lfs.service.ArticleService;
import com.lfs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//在应用启动时把博客的浏览量存储到redis中
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;


    @Override
    public void run(String... args) throws Exception {
//        查询博客信息 我们需要: id viewCount
        List<Article> articles = articleMapper.selectList(null);
//        要把long类型转为integer类型，long类型在redis(1L)中不能递增,通过intValue()来转换
        Map<String, Integer> ViewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));

//        存储到redis
        redisCache.setCacheMap("article:viewCount", ViewCountMap);
    }
}
