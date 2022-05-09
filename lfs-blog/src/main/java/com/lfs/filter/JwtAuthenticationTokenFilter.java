package com.lfs.filter;

import com.alibaba.fastjson.JSON;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.LoginUser;
import com.lfs.enums.AppHttpCodeEnum;
import com.lfs.utils.JwtUtil;
import com.lfs.utils.RedisCache;
import com.lfs.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        获取请求头中的token
        String token = request.getHeader("token");
//        判断是否存在token
        if (!StringUtils.hasText(token)) {
//            说明该接口没有token，直接放行
            filterChain.doFilter(request, response);
            return;
        }
//        解析获取userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
//            token超时，非法了
//            响应告诉前端需要重新登录
//            这里采用了WebUtils下的工具类
//            他会直接返回给页面一个401的状态码，并设置一下格式，然后将错误的信息写回给页面
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//            在Controller中因为受到RestController的管理会返回(转换为)Json数据，但在Filter中不会，所以我们要自己返回json格式的数据
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

//        从Redis中根据userID获取用户信息(解析JWT)
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
//        登录超时，获取不到用户信息了(消息过期消失)，如果获取不到
        if (Objects.isNull(loginUser)) {
//            登录过期，提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
//        存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

