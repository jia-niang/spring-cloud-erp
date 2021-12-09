package com.kabunx.erp.exception;

public enum DBExceptionEnum {
    NOT_FOUND("E000001P422", "没有查询结果"),
    MULTIPLE_RECORDS("E000001P422", "存在多条记录");

    private final String code;
    private final String message;

    DBExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
