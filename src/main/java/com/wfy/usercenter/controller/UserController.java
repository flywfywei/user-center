package com.wfy.usercenter.controller;

import com.wfy.usercenter.common.params.LoginRequest;
import com.wfy.usercenter.common.params.RegisterRequest;
import com.wfy.usercenter.common.result.LoginRegisterResult;
import com.wfy.usercenter.common.result.ResponseResult;
import com.wfy.usercenter.common.result.ResultUtils;
import com.wfy.usercenter.exception.BusinessException;
import com.wfy.usercenter.model.domain.User;
import com.wfy.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wfy.usercenter.common.ResponseCode.*;
import static com.wfy.usercenter.constant.UserConstant.USER_LOGIN_INFO;

/**
 * 用户控制类
 * @author wfy
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/login")
    public ResponseResult<LoginRegisterResult> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
        if (loginRequest == null) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        String userAccount = loginRequest.getUserAccount();
        String password = loginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        LoginRegisterResult result = userService.userLogin(loginRequest.getType(), userAccount, password, request);
        return ResultUtils.success(result);
    }
    @GetMapping("/current")
    public ResponseResult<User> currentUser(HttpServletRequest request){
        User currentUser = (User) request.getSession().getAttribute(USER_LOGIN_INFO);
        if (currentUser == null) {
            throw new BusinessException(NO_LOGIN_ERROR);
        }
        Long currentUserId = currentUser.getId();
        User user = userService.getById(currentUserId);
        User secureUser = userService.getSecureUser(user);
        return ResultUtils.success(secureUser);
    }
    @PostMapping("/outLogin")
    public ResponseResult<Boolean> outLogin(HttpServletRequest request){
        boolean result = userService.userOutLogin(request);
        return ResultUtils.success(result);
    }
    @PostMapping("/register")
    public ResponseResult<LoginRegisterResult> register(@RequestBody RegisterRequest registerRequest){
        if (registerRequest == null) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        String userAccount = registerRequest.getUserAccount();
        String password = registerRequest.getPassword();
        String checkPassword = registerRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        userService.userRegister(userAccount, password, checkPassword);
        LoginRegisterResult result = new LoginRegisterResult("ok", "account");
        return ResultUtils.success(result);
    }
    @GetMapping("/search")
    public ResponseResult<List<User>> searchUser(String username, HttpServletRequest request){
        List<User> userList = userService.userSearch(username, request);
        return ResultUtils.success(userList);
    }
    @DeleteMapping("/delete")
    public ResponseResult<Boolean> delete(Long id, HttpServletRequest request){
        if (id == null || id < 0) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        boolean result = userService.userDelete(id, request);
        return ResultUtils.success(result);
    }
}
