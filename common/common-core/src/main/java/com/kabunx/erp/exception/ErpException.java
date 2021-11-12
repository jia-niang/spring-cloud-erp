package com.kabunx.erp.exception;

/**
 * 统一的API异常类
 */
public class ErpException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public ErpException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public ErpException(String message) {
        super(message);
    }

    public ErpException(Throwable cause) {
        super(cause);
    }

    public ErpException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionEnum getErrorCode() {
        return exceptionEnum;
    }
}
