package com.psf.common.exception;

import com.psf.common.constant.enums.ErrorLevel;
import lombok.Data;

@Data
public class CommonException extends RuntimeException {

    private int code;

    private ErrorLevel errorLevel;

    public CommonException(Integer code, String errorMsg) {
        super(errorMsg);
        this.code = code;
        this.errorLevel = ErrorLevel.NORMAL;
    }

    public CommonException(Integer code, String errorMsg, ErrorLevel level){
        super(errorMsg);
        this.code = code;
        this.errorLevel = level;
    }

    public CommonException(Integer code, String errorMsg, ErrorLevel level, Exception e) {
        super(errorMsg, e);
        this.code = code;
        this.errorLevel = level;
    }
}
