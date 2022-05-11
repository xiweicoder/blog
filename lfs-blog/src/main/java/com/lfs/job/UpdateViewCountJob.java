package com.lfs.job;

import com.lfs.domain.entity.Article;
import com.lfs.service.ArticleService;
import com.lfs.utils.RedisCache;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//3、
//Redis数据定时同步到数据库中
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    //    cron 表达式：间隔时间点
    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCountJob() {
//        获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles = viewCountMap.entrySet()
                .stream()
//                在 此new 了一个构造函数更加方便转换
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

//        更新到数据库中 根据Id批量处理数据到数据库中
        articleService.updateBatchById(articles);
    }
}
