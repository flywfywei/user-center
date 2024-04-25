package com.wfy.usercenter.common;

import lombok.Getter;

/**
 * @author wfy
 * @version 1.0
 * 响应状态码
 */
@Getter
public enum ResponseCode {
    PARAMS_ERROR(40000, "Request parameter error", "请求参数错误"),
    NO_LOGIN_ERROR(40100, "User not login", "用户未登录"),
    NO_AUTH_ERROR(40200, "User doesn't have permission", "用户没有权限"),
    ACCESS_PROHIBIT_ERROR(40300, "User has permission, but access is prohibited", "用户得到权限，但访问禁止"),
    SYSTEM_ERROR(50000, "Internal system exception", "系统内部异常"),
    REQUEST_OK(200, "ok", "请求正常响应");
    private final int code;
    private final String message;
    private final String description;

    ResponseCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
