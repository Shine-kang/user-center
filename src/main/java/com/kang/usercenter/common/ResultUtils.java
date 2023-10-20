package com.kang.usercenter.common;

import lombok.Data;

/**
 * @author:KANG
 * @version:1.0
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse(0,data,"");
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode.getCode(),errorCode.getMessage(),errorCode.getDescription());
    }
    public static <T> BaseResponse<T> error(int code,String message,String description){
        return new BaseResponse<>(code,message,description);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode,String message){
        return new BaseResponse(errorCode.getCode(),message);
    }


}
