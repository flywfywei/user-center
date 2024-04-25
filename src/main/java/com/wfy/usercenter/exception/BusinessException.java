package com.wfy.usercenter.exception;

import com.wfy.usercenter.common.ResponseCode;
import lombok.Getter;

import java.io.Serial;

/**
 * @author wfy
 * @version 1.0
 * @description 业务异常
 */
@Getter
public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 4085641881897691613L;
    private final int code;
    private final String description;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.description = "";
    }

    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.description = responseCode.getDescription();
    }
    public BusinessException(ResponseCode responseCode, String description) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.description = description;
    }
}
