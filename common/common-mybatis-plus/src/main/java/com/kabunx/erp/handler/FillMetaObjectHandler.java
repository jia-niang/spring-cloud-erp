package com.kabunx.erp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kabunx.erp.config.FillConfig;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class FillMetaObjectHandler implements MetaObjectHandler {
    @Resource
    FillConfig fillConfig;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (isSecondTimestamp()) {
            this.strictInsertFill(metaObject, fillConfig.getCreatedColumn(), this::getSeconds, Integer.class);
            this.strictInsertFill(metaObject, fillConfig.getUpdatedColumn(), this::getSeconds, Integer.class);
        } else {
            this.strictInsertFill(metaObject, fillConfig.getCreatedColumn(), this::getMillis, Long.class);
            this.strictInsertFill(metaObject, fillConfig.getUpdatedColumn(), this::getMillis, Long.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (isSecondTimestamp()) {
            this.strictInsertFill(metaObject, fillConfig.getUpdatedColumn(), this::getSeconds, Integer.class);
        } else {
            this.strictInsertFill(metaObject, fillConfig.getUpdatedColumn(), this::getMillis, Long.class);
        }
    }

    private boolean isSecondTimestamp() {
        return "s".equals(fillConfig.getTimestamp());
    }

    private Integer getSeconds() {
        // Convert given time (milliseconds) to seconds
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    private Long getMillis() {
        return System.currentTimeMillis();
    }
}
