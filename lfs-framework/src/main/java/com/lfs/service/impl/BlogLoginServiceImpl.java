package com.lfs.service.impl;

import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.LoginUser;
import com.lfs.domain.entity.User;
import com.lfs.domain.vo.BlogUserLoginVo;
import com.lfs.domain.vo.UserInfoVo;
import com.lfs.service.BlogLoginService;
import com.lfs.utils.BeanCopyUtils;
import com.lfs.utils.JwtUtil;
import com.lfs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    //    登录
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或者密码错误");
        }
//        获取userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
//        把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        //把token和userinfo封装 返回 创建了 两个Vo来返回数据
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    //    登出
    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }
}
