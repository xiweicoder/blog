package com.lfs.handler.exception;

import com.lfs.domain.ResponseResult;
import com.lfs.enums.AppHttpCodeEnum;
import com.lfs.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;


//从运行时异常 ——> SystemException(状态码，和消息，交给global管控) ——> GlobalExceptionHandler
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
//        打印异常信息
        log.error("出现了异常！ {0}",e);
//        log.error(MessageFormat.format("出现了异常： {0}", e));
//        从异常对象中获取提示信息封装返回(响应给前端)
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    //    出现其他的异常，不受我们控制
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
//        打印异常信息
        log.error("出现了异常！ {0}",e);
//        log.error(MessageFormat.format("出现了异常： {0}", e));
//        从异常对象中获取提示信息封装返回(响应给前端)
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

}
