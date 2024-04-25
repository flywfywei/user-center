package com.wfy.usercenter.common;

import com.wfy.usercenter.common.result.ResponseResult;
import com.wfy.usercenter.common.result.ResultUtils;
import com.wfy.usercenter.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.wfy.usercenter.common.ResponseCode.SYSTEM_ERROR;

/**
 * @author wfy
 * @version 1.0
 * @description 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<String> businessExceptionHandler(BusinessException e) {
        log.info("BusinessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult<String> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error(SYSTEM_ERROR);
    }
}
