package com.kabunx.erp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kabunx.erp.property.MetaProperties;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class FillMetaObjectHandler implements MetaObjectHandler {
    @Resource
    MetaProperties metaProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.autoInsertFill(metaObject, metaProperties.getCreatedColumn());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.autoInsertFill(metaObject, metaProperties.getUpdatedColumn());
    }

    private void autoInsertFill(MetaObject metaObject, String fieldName) {
        switch (metaProperties.getTimestamp()) {
            case "seconds":
                this.strictInsertFill(metaObject, fieldName, this::getSeconds, Integer.class);
                break;
            case "millis":
                this.strictInsertFill(metaObject, fieldName, this::getMillis, Long.class);
                break;
            case "datetime":
                this.strictInsertFill(metaObject, fieldName, this::getDatetime, String.class);
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

    private String getDatetime() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return ft.format(new Date());
    }
}
