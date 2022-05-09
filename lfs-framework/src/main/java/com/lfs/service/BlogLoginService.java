package com.lfs.service;

import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.User;

public interface BlogLoginService {
    //    登录
    ResponseResult login(User user);

    //    登出
    ResponseResult logout();
}
