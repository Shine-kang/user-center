package com.kang.usercenter.common;

/**
 * @author:KANG
 * @version:1.0
 */
public enum ErrorCode {
    PARAMS_ERROR(40000,"请求参数错误",""),
    PARAMS_NULL(400001,"参数为空",""),
    NO_AUTH(40100,"无权限",""),
    NOT_LOGIN(40101,"未登录",""),
    SYSTEM_ERROR(50000,"系统内部异常","");
    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
