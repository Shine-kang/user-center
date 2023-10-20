package com.kang.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:KANG
 * @version:1.0
 */
@Data
public class UserLoginRequest implements Serializable {
    private String userAccount;
    private String userPassword;

}
