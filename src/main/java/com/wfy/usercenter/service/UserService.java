package com.wfy.usercenter.service;

import com.wfy.usercenter.common.result.LoginRegisterResult;
import com.wfy.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author wfy
* @description 针对表【user】的数据库操作Service
* @createDate 2024-03-24 04:01:11
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账户
     * @param password 密码
     * @param checkPassword 校验密码
     * @return id
     */
    long userRegister(String userAccount, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param account  账户
     * @param password 密码
     * @param request  会话域
     * @return user
     */
    LoginRegisterResult userLogin(String type, String account, String password, HttpServletRequest request);

    boolean hasPermissions(HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param user user
     * @return secureUser安全的用户
     */
    User getSecureUser(User user);

    /**
     * 查询用户列表
     * @param username 用户昵称
     * @param request 请求
     * @return 用户列表
     */
    List<User> userSearch(String username, HttpServletRequest request);

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户id
     * @param request 请求
     * @return 是否删除
     */
    boolean userDelete(Long id, HttpServletRequest request);

    /**
     * 退出登录
     *
     * @param request 请求
     * @return 是否成功
     */
    boolean userOutLogin(HttpServletRequest request);
}
