package com.kang.usercenter.service;

import com.kang.usercenter.common.BaseResponse;
import com.kang.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 26444
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-10-11 21:02:29
*/
public interface UserService extends IService<User> {
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafetyUser(User user);

    int userLogout(HttpServletRequest request);
}
