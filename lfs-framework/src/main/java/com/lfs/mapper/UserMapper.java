package com.lfs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lfs.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-07 16:38:23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

