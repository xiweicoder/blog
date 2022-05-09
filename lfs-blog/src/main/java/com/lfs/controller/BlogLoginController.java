package com.lfs.controller;

import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.User;
import com.lfs.enums.AppHttpCodeEnum;
import com.lfs.exception.SystemException;
import com.lfs.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    //    登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
//            提示必须要传用户名，有人不正常登录，没有经过表单校验
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

//    登出
    @PostMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }

}
