package com.lfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lfs.domain.entity.LoginUser;
import com.lfs.domain.entity.User;
import com.lfs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

//        判断是否查到用户，如果没查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }

//        返回用户信息
//        todo 查询用户权限信息封装

//        直接返回UserDetails的user返回不了，
//        因为UserDetails是接口，我们需要自己实现(LoginUser)接口
        return new LoginUser(user);
    }
}