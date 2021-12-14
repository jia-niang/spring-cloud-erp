package com.kabunx.erp.exception;

public class PlusException extends BizException {
    PlusExceptionEnum plusExceptionEnum;

    public PlusException(PlusExceptionEnum plusExceptionEnum) {
        super(plusExceptionEnum.getMessage());
        this.plusExceptionEnum = plusExceptionEnum;
    }

    public PlusException(String message) {
        super(message);
    }

    public PlusExceptionEnum getPlusExceptionEnum() {
        return plusExceptionEnum;
    }
}
