package com.kabunx.erp.exception;

public class GatewayException extends BizException{
    public GatewayException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }

    public GatewayException(String message) {
        super(message);
    }

    public GatewayException(Throwable cause) {
        super(cause);
    }

    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
