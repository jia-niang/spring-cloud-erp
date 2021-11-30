package com.kabunx.erp.validator;

public enum ValidatorEnum {
    NULL(0, "null"),
    SMS_CODE_SECRET(1, "sms-code"),
    LOGIN_ACCOUNT_SECRET(2, "login-account");

    private final int code;
    private final String type;

    ValidatorEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
