package com.wfy.usercenter.common.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wfy
 * @version 1.0
 * 响应结果
 */
@Data
public class ResponseResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 5728759158670086956L;
    public int code;
    public T data;
    public String message;
    public String description;
    //当前权限
    public String currentAuthority;

    public ResponseResult(int code, T data) {
        this(code, data, "");
    }

    public ResponseResult(int code, String message) {
        this(code, message, "");
    }

    public ResponseResult(int code, T data, String message) {
        this(code, data, message, "");
    }

    public ResponseResult(int code, String message, String description) {
        this(code, message, description, "");
    }

    public ResponseResult(int code, T data, String message, String description) {
        this(code, data, message, description, "");
    }

    public ResponseResult(int code, String message, String description, String currentAuthority) {
        this(code, null, message, description, currentAuthority);
    }

    public ResponseResult(int code, T data, String message, String description, String currentAuthority) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
        this.currentAuthority = currentAuthority;
    }
}
