package com.lfs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lfs.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-07 14:32:34
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

