package com.wfy.usercenter.common.params;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求参数
 * @author wfy
 * @version 1.0
 */
@Data
public class LoginRequest implements Serializable {
    private String userAccount;
    private String password;
    private String type;
    @Serial
    private static final long serialVersionUID = -7166924802216743855L;
}
