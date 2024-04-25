package com.wfy.usercenter.common.params;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求参数
 * @author wfy
 * @version 1.0
 */
@Data
public class RegisterRequest implements Serializable {
    private String userAccount;
    private String password;
    private String checkPassword;
    @Serial
    private static final long serialVersionUID = 8838148019332158585L;
}
