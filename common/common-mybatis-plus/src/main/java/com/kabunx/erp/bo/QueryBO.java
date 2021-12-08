package com.kabunx.erp.bo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.pojo.BaseDTO;
import com.kabunx.erp.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class QueryBO<T> extends BaseDTO {
    private long page = 1L;
    private long pageSize = 10L;

    private final QueryWrapper<T> queryWrapper = new QueryWrapper<>();

    public Page<T> getPage() {
        return new Page<>(page, pageSize);
    }

    /**
     * 通过反射动态拼接查询
     */
    public QueryWrapper<T> getWrapper() {
        Class<?> queryClass = this.getClass();
        // 获取当前类定义的属性（不包括父类）
        Field[] fields = queryClass.getDeclaredFields();
        for (Field field : fields) {
            int fieldMod = field.getModifiers();
            // final将被忽略
            if (Modifier.isFinal(fieldMod)) {
                continue;
            }
            String fieldName = field.getName();
            String methodName = "where" + StringUtils.capitalize(fieldName);
            try {
                Method whereMethod = queryClass.getDeclaredMethod(methodName, field.getType());
                // TODO 格式转换
                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null) {
                    whereMethod.setAccessible(true);
                    whereMethod.invoke(this, field.get(this));
                }
            } catch (Exception ignored) {
                log.warn("未定义的查询函数{}", methodName);
            }
        }
        return queryWrapper;
    }
}
