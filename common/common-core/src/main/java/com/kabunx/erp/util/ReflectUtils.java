package com.kabunx.erp.util;

import java.lang.reflect.Field;

public class ReflectUtils {
    /**
     * 备用方法
     * 反射机制获取属性值
     */
    public static Object getDeclaredFieldValue(Object object, String fieldName) {
        // 我们约定bean的属性为驼峰模式
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            return null;
        }
    }
}
