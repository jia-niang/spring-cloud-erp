package com.kabunx.erp.exception;

public enum PlusExceptionEnum {
    NOT_INIT_DTO("E000001P411", "没有初始化DTO"),
    NOT_INIT_WRAPPER("E000001P411", "没有初始化Wrapper"),
    NOT_BUILT("E000001P411", "没有被构建的查询"),
    NOT_FOUND("E000001P422", "没有查询结果"),
    MULTIPLE_RECORDS("E000001P422", "存在多条记录");

    private final String code;
    private final String message;

    PlusExceptionEnum(String code, String message) {
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
