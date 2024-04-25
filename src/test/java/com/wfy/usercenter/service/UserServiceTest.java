package com.wfy.usercenter.service;

import com.wfy.usercenter.common.result.LoginRegisterResult;
import com.wfy.usercenter.exception.BusinessException;
import com.wfy.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author wfy
 * @version 1.0
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;

    @Test
    void testUserAdd(){
        val user = new User();
        user.setUserAccount("root");
        user.setPassword("root");
        user.setUsername("柚子");
        user.setAvatarUrl("");
        user.setEmail("root@wfy.com");
        user.setSex(0);
        val save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);
    }

    @Test
    void userRegister() {
        String userAccount = "testUserRegister";
        String password = "";
        String checkPassword = "testUserRegister";
        long result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "te";
        password = "testUserRegister";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "testUserRegister";
        password = "testUs";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "testUser Register";
        password = "testUserRegister";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "testUserRegister";
        password = "testUserRegister1";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "root";
        password = "testUserRegister";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "testUserRegister";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void userLogin() {
        String userAccount = "testUserRegister";
        String password = "";
        String type = "";
        LoginRegisterResult result = null;
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (BusinessException e) {
            Assertions.assertEquals("请求参数为空！", e.getDescription());
        }
        Assertions.assertNull(result);

        userAccount = "te";
        password = "testUserRegister";
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (BusinessException e) {
            Assertions.assertEquals("Request parameter error", e.getMessage());
        }
        Assertions.assertNull(result);

        userAccount = "testUserRegister";
        password = "testUs";
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (Exception e) {
            Assertions.assertEquals("Request parameter error", e.getMessage());
        }
        Assertions.assertNull(result);

        userAccount = "testUser Register";
        password = "testUserRegister";
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (Exception e) {
            Assertions.assertEquals("Request parameter error", e.getMessage());
        }
        Assertions.assertNull(result);

        userAccount = "testUserRegister1";
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (Exception e) {
            Assertions.assertEquals("Request parameter error", e.getMessage());
        }
        Assertions.assertNull(result);

        userAccount = "testUserRegister";
        password = "testUserRegister1";
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (Exception e) {
            Assertions.assertEquals("Request parameter error", e.getMessage());
        }
        Assertions.assertNull(result);

        password = "testUserRegister";
        try {
            result = userService.userLogin(type, userAccount, password, request);
        } catch (Exception e) {
            Assertions.assertEquals("Request parameter error", e.getMessage());
        }
        Assertions.assertNotNull(result);
    }
}