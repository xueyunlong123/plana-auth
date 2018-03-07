package com.scaff.utils.exception;

import com.scaff.utils.exception.IErrorCode;

import lombok.Getter;

/**
 * Created by chenwen on 16/12/19.
 */
//@AllArgsConstructor
public enum WebBusinessCodeEnum implements IErrorCode {
    //服务相关
    SERVICE_NOT_EXIST("服务不存在", false),
    SERVICE_AUTH_SUCCESS("服务授权成功", false),
    SERVICE_NOT_AUTH("服务未授权", false),

    //商户相关
    MERCHANTS_NOT_EXIST("商户不存在",false),
    INSUFFICIENT_BALANCE("账户余额不足",false),
    DECRYPT_DATA_FAILED("数据解密失败",false),

    //role相关
    ROLE_ALREADY_EXSIT("角色已创建",false),
    ;

    @Getter
    private final String message;

    @Getter
    private final boolean isUnknownException;

    WebBusinessCodeEnum(String message){
        this(message, true);
    }

    WebBusinessCodeEnum(String message, boolean isUnknownException){
        this.message = message;
        this.isUnknownException = isUnknownException;
    }

    @Override
    public String getCode() {
        return getCode(WebBusinessStartCode,ordinal());
    }
}
