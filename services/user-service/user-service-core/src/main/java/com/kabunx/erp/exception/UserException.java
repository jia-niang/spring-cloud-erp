package com.kabunx.erp.exception;

public class UserException extends BizException {

    public UserException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
