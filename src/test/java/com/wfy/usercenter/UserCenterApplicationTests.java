package com.wfy.usercenter;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
        String userAccount = null;
        String password = "55555";
        String checkPassword = "55555";
        Assertions.assertTrue(StringUtils.isAnyBlank(userAccount, password, checkPassword));
    }

}
