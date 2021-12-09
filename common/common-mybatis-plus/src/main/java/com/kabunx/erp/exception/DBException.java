package com.kabunx.erp.exception;

public class DBException extends BizException {
    DBExceptionEnum dbExceptionEnum;

    public DBException(DBExceptionEnum dbExceptionEnum) {
        super(dbExceptionEnum.getMessage());
        this.dbExceptionEnum = dbExceptionEnum;
    }

    public DBException(String message) {
        super(message);
    }
}
