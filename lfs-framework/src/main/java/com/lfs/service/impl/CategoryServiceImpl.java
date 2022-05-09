package com.lfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfs.constants.SystemConstants;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Article;
import com.lfs.domain.vo.CategoryVo;
import com.lfs.domain.entity.Category;
import com.lfs.mapper.CategoryMapper;
import com.lfs.service.ArticleService;
import com.lfs.service.CategoryService;
import com.lfs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-05-07 09:36:06
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    //    页面显示分类列表
    @Override
    public ResponseResult getCategoryList() {
//        查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);


//        获取文章的分类id，并去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

//        查询分类表，状态为正常
        List<Category> categories = listByIds(categoryIds);
        categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

//        封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

