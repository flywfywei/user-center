package com.wfy.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wfy.usercenter.common.result.LoginRegisterResult;
import com.wfy.usercenter.exception.BusinessException;
import com.wfy.usercenter.mapper.UserMapper;
import com.wfy.usercenter.model.domain.User;
import com.wfy.usercenter.service.UserService;
import com.wfy.usercenter.utils.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.wfy.usercenter.common.ResponseCode.*;
import static com.wfy.usercenter.constant.UserConstant.USER_LOGIN_INFO;

/**
 * @author wfy
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-03-24 04:01:11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {
        //非空与长度校验
        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(PARAMS_ERROR, "账号长度过短！");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(PARAMS_ERROR, "密码长度过短！");
        }
        if (userAccount.length() > 32) {
            throw new BusinessException(PARAMS_ERROR, "账号长度过长！");
        }
        if (password.length() > 32 || checkPassword.length() > 32) {
            throw new BusinessException(PARAMS_ERROR, "密码长度过长！");
        }
        //账号不能包含特殊字符
        String regEx = "\\pP|\\pS|\\s+";
        if (Pattern.compile(regEx).matcher(userAccount).find()) {
            throw new BusinessException(PARAMS_ERROR, "账号包含违规字符！");
        }
        //密码与校验密码一致
        if (!password.equals(checkPassword)) {
            throw new BusinessException(PARAMS_ERROR, "密码与校验密码不一致！");
        }
        //账号在数据库中不能重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ACCESS_PROHIBIT_ERROR, "账号在数据库中重复！");
        }
        //密码加密
        String newPassword = PasswordEncoder.encode(password);
        //插入数据库（注册）
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(newPassword);
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(SYSTEM_ERROR, "账号创建失败！");
        }
        return user.getId();
    }

    @Override
    public LoginRegisterResult userLogin(String type, String userAccount, String password, HttpServletRequest request) {
        //非空与长度校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(PARAMS_ERROR, "账号长度过短！");
        }
        if (password.length() < 8) {
            throw new BusinessException(PARAMS_ERROR, "密码长度过短！");
        }
        if (userAccount.length() > 32) {
            throw new BusinessException(PARAMS_ERROR, "账号长度过长！");
        }
        if (password.length() > 32) {
            throw new BusinessException(PARAMS_ERROR, "密码长度过长！");
        }
        //账户不能包含特殊字符
        String regEx = "\\pP|\\pS|\\s+";
        if (Pattern.compile(regEx).matcher(userAccount).find()) {
            throw new BusinessException(PARAMS_ERROR, "账号包含违规字符！");
        }
        //查验数据库
        //todo 查数据库后可保存至缓存3-5min防止同一用户账号爆破
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(PARAMS_ERROR, "账号或密码错误！");
        }
        //密码校验
        Boolean result = PasswordEncoder.matches(user.getPassword(), password);
        if (!result) {
            throw new BusinessException(PARAMS_ERROR, "账号或密码错误！");
        }
        //用户信息脱敏安全
        User handleUser = getSecureUser(user);
        //保存在会话中（单点登录）
        //todo redis分布式登录
        request.getSession().setAttribute(USER_LOGIN_INFO, handleUser);
        return new LoginRegisterResult("ok", type);
    }

    @Override
    public List<User> userSearch(String username, HttpServletRequest request) {
        if (hasPermissions(request)){
            throw new BusinessException(NO_AUTH_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), User::getUsername, username);
        List<User> list = list(queryWrapper);
        return list.stream().map(this::getSecureUser).collect(Collectors.toList());
    }

    @Override
    public boolean userDelete(Long id, HttpServletRequest request) {
        if (id == null || id < 0) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        if (hasPermissions(request)){
            throw new BusinessException(NO_AUTH_ERROR);
        }
        return removeById(id);
    }

    @Override
    public boolean userOutLogin(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_INFO);
        return true;
    }

    @Override
    public boolean hasPermissions(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_INFO);
        return user.getRole() != 1;
    }

    @Override
    public User getSecureUser(User user){
        if (user == null) {
            throw new BusinessException(PARAMS_ERROR, "请求参数为空！");
        }
        //用户信息脱敏安全
        User handleUser = new User();
        handleUser.setId(user.getId());
        handleUser.setUserAccount(user.getUserAccount());
        handleUser.setUsername(user.getUsername());
        handleUser.setAvatarUrl(user.getAvatarUrl());
        handleUser.setRole(user.getRole());
        handleUser.setEmail(user.getEmail());
        handleUser.setSex(user.getSex());
        handleUser.setState(user.getState());
        handleUser.setCreateTime(user.getCreateTime());
        return handleUser;
    }
}




