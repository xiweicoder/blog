package com.lfs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.User;
import com.lfs.domain.vo.UserInfoVo;
import com.lfs.mapper.UserMapper;
import com.lfs.service.UserService;
import com.lfs.utils.BeanCopyUtils;
import com.lfs.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author CodeShark
 * @since 2022-05-07 16:38:23
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //    个人信息
    @Override
    public ResponseResult userInfo() {
//        获取当前的用户ID
        Long userId = SecurityUtils.getUserId();
//        根据用户ID查询用户信息
        User user = getById(userId);
//        封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    //    修改个人信息
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
}

