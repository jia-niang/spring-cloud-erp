package com.kabunx.erp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kabunx.erp.property.MetaProperties;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class FillMetaObjectHandler implements MetaObjectHandler {
    @Resource
    MetaProperties metaProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        switch (metaProperties.getTimestamp()) {
            case "seconds":
                this.strictInsertFill(metaObject, metaProperties.getCreatedColumn(), this::getSeconds, Integer.class);
                break;
            case "millis":
                this.strictInsertFill(metaObject, metaProperties.getCreatedColumn(), this::getMillis, Long.class);
                break;
            case "datetime":
                this.strictInsertFill(metaObject, metaProperties.getCreatedColumn(), this::getDatetime, Date.class);
                break;
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        switch (metaProperties.getTimestamp()) {
            case "seconds":
                this.strictUpdateFill(metaObject, metaProperties.getUpdatedColumn(), this::getSeconds, Integer.class);
                break;
            case "millis":
                this.strictUpdateFill(metaObject, metaProperties.getUpdatedColumn(), this::getMillis, Long.class);
                break;
            case "datetime":
                this.strictUpdateFill(metaObject, metaProperties.getUpdatedColumn(), this::getDatetime, Date.class);
                break;
        }
    }

    private Integer getSeconds() {
        // Convert given time (milliseconds) to seconds
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    private Long getMillis() {
        return System.currentTimeMillis();
    }

    private Date getDatetime() {
        return new Date();
    }
}
