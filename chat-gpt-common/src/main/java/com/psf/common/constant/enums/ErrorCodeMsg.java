package com.psf.common.constant.enums;


import com.psf.common.exception.CommonException;

public enum ErrorCodeMsg {

    SUCCESS(0, "success", ErrorLevel.NORMAL),
    MISSING_PARAMS(4000, "参数缺失", ErrorLevel.NORMAL),
    BAD_REQUEST(4001, "参数异常", ErrorLevel.NORMAL),
    PERMISSION_DENIED(4002, "无权操作", ErrorLevel.NORMAL),
    ;

    private final int code;

    private final String defaultMessage;

    private final ErrorLevel errorLevel;

    ErrorCodeMsg(int code, String message, ErrorLevel errorLevel) {
        this.code = code;
        this.defaultMessage = message;
        this.errorLevel = errorLevel;
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    public CommonException newException() {
        return new CommonException(this.code, this.defaultMessage, this.errorLevel);
    }

    public CommonException newExceptionWithCustomerMsg(String message) {
        return new CommonException(code, message, errorLevel);
    }

    public CommonException newExceptionWithException(Exception e) {
        return new CommonException(code, defaultMessage, errorLevel, e);
    }
}
