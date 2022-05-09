package com.lfs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lfs.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-07 09:36:05
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

