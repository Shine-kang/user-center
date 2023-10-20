package com.kang.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author:KANG
 * @version:1.0
 */
@Data
public class UserRegisterRequest implements Serializable {
//    private static final long serialVersionUID = 0;
    private String userAccount;
    private String userPassword;
    private String checkPassword;

    private String planetCode;
}
