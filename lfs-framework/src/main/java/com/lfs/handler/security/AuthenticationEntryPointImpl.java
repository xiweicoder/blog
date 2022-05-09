package com.lfs.handler.security;

import com.alibaba.fastjson.JSON;
import com.lfs.domain.ResponseResult;
import com.lfs.enums.AppHttpCodeEnum;
import com.lfs.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//认证失败处理
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        打印在控制台一份错误
        authException.printStackTrace();

        //InsufficientAuthenticationException
        //BadCredentialsException
//        对返回的异常对象进行判断，来返回相应的状态码和信息
        ResponseResult result = null;
        if (authException instanceof BadCredentialsException) {
//            用户名或密码错误
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
//            需要登录后操作
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }

        //        响应前端
        WebUtils.renderString(response, JSON.toJSONString(result));

    }
}
