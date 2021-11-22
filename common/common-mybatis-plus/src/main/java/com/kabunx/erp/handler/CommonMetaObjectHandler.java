package com.kabunx.erp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

    private Integer getCurrentTimestamp() {
        // Convert given time (milliseconds) to seconds
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }
}
