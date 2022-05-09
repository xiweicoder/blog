package com.lfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author CodeShark
 * @since 2022-05-07 16:38:23
 */
public interface UserService extends IService<User> {

    //    个人信息
    ResponseResult userInfo();

    //    修改个人信息
    ResponseResult updateUserInfo(User user);
}

