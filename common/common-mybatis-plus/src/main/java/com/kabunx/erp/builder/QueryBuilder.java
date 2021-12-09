package com.kabunx.erp.builder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.dto.QueryDTO;
import com.kabunx.erp.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 构造器，将前端参数自定映射到自定义wrapper查询
 */
@Slf4j
public class QueryBuilder<T> {
    private static final List<String> ignoreParams = Arrays.asList("page", "pageSize");

    private final QueryDTO query;

    private final QueryWrapper<T> queryWrapper;

    public QueryBuilder(
            QueryDTO query,
            QueryWrapper<T> queryWrapper
    ) {
        this.query = query;
        this.queryWrapper = queryWrapper;
    }

    public Page<T> getQueryPage() {
        return new Page<>(query.getPage(), query.getPageSize());
    }

    public QueryWrapper<T> getQueryWrapper() {
        Class<?> queryClass = query.getClass();
        Class<?> wrapperClass = queryWrapper.getClass();
        // 获取当前类定义的属性（不包括父类）
        Field[] fields = queryClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            // 忽略分页参数
            if (ignoreParams.contains(fieldName)) {
                continue;
            }
            String methodName = "where" + StringUtils.capitalize(fieldName);
            try {
                Method whereMethod = wrapperClass.getDeclaredMethod(methodName, field.getType());
                field.setAccessible(true);
                Object value = field.get(query);
                if (value == null) {
                    continue;
                }
                whereMethod.setAccessible(true);
                whereMethod.invoke(queryWrapper, value);
            } catch (Exception ignored) {
                log.warn("反射异常【field: {}, method: {}】", fieldName, methodName);
            }
        }
        return queryWrapper;
    }
}
