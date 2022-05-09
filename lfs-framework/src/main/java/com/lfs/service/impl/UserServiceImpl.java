package com.lfs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfs.domain.entity.User;
import com.lfs.mapper.UserMapper;
import com.lfs.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-05-07 16:38:23
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

