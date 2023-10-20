package com.kang.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:KANG
 * @version:1.0
 */
@Data
public class BaseResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private String description;

    public BaseResponse(int code, String message, T data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }
    public BaseResponse(int code,T data,String message){
        this(code,message,data,"");
    }

    public BaseResponse(int code,String message,String description){
        this(code,message,null,description);
    }

    public BaseResponse(int code,T data){
        this(code,data,"");
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),errorCode.getMessage(),null,errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode,String message,String description){
        this(errorCode.getCode(),message,null,description);
    }

    public BaseResponse(ErrorCode errorCode,String message){
        this(errorCode.getCode(),message,null,errorCode.getDescription());
    }
}
