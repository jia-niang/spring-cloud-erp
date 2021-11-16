package com.kabunx.erp.exception;

public class AuthException extends BizException {
    public AuthException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
