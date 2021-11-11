package com.kabunx.erp.exception;

/**
 * 统一的API异常类
 */
public class ApiException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public ApiException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionEnum getErrorCode() {
        return exceptionEnum;
    }
}
