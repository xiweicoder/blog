package com.lfs.handler.security;

import com.alibaba.fastjson.JSON;
import com.lfs.domain.ResponseResult;
import com.lfs.enums.AppHttpCodeEnum;
import com.lfs.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//授权失败处理器
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //        打印在控制台一份错误
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //        响应前端
        WebUtils.renderString(response, JSON.toJSONString(result));

    }
}
