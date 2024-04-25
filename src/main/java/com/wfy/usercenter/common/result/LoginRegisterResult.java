package com.wfy.usercenter.common.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wfy
 * @version 1.0
 * @description 登录注册返回结果
 */
@Data
public class LoginRegisterResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 2641108580744494058L;
    public String status;
    public String type;
    public String currentAuthority;

    public LoginRegisterResult() {
        super();
    }

    public LoginRegisterResult(String status, String type) {
        this.status = status;
        this.type = type;
    }

    public LoginRegisterResult(String status, String type, String currentAuthority) {
        this.status = status;
        this.type = type;
        this.currentAuthority = currentAuthority;
    }
}
