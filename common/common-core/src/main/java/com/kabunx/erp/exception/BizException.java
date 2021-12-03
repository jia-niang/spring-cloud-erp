package com.kabunx.erp.exception;

/**
 * 统一的API异常类
 */
public class BizException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
