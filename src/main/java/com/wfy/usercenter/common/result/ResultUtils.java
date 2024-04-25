package com.wfy.usercenter.common.result;

import com.wfy.usercenter.common.ResponseCode;

/**
 * @author wfy
 * @version 1.0
 * @description 返回结果工具类
 */
public class ResultUtils {
    public static <T> ResponseResult<T> success(T data){
        return success(ResponseCode.REQUEST_OK, data);
    }
    /**
     * 正常响应
     * @param responseCode 响应状态码
     * @return 响应结果
     * @param <T> 响应数据data
     */
    public static <T> ResponseResult<T> success(ResponseCode responseCode, T data){
        return new ResponseResult<>(responseCode.getCode(), data, responseCode.getMessage(), responseCode.getDescription());
    }

    public static <T> ResponseResult<T> error(ResponseCode responseCode){
        return new ResponseResult<>(responseCode.getCode(), responseCode.getMessage(), responseCode.getDescription());
    }

    /**
     * 异常响应
     * @param responseCode 响应状态码
     * @param description 异常描述（详细）
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error(ResponseCode responseCode, String description){
        return new ResponseResult<>(responseCode.getCode(), responseCode.getMessage(), description);
    }

    public static <T> ResponseResult<T>  error(int code, String message, String description) {
        return new ResponseResult<>(code, message, description);
    }
}
