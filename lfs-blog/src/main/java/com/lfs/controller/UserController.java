package com.lfs.controller;

import com.lfs.annotation.SystemLog;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.User;
import com.lfs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //    展示个人信息
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    //    修改个人信息
    @SystemLog(BusinessName = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    //    注册功能
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}
