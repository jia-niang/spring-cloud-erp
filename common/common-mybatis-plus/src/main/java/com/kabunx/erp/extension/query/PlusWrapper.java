package com.kabunx.erp.extension.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.dto.QueryDTO;
import com.kabunx.erp.exception.PlusException;
import com.kabunx.erp.exception.PlusExceptionEnum;
import com.kabunx.erp.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PlusWrapper<T> extends QueryWrapper<T> {

    private static final List<String> ignores = Arrays.asList("page", "pageSize");

    protected QueryDTO queryDTO;

    public PlusWrapper() {}

    public PlusWrapper(QueryDTO queryDTO) {
        this.queryDTO = queryDTO;
    }

    public Integer getPage() {
        requireNonNull();
        return queryDTO.getPage();
    }

    public Integer getPageSize() {
        requireNonNull();
        return queryDTO.getPageSize();
    }

    public void build() {
        requireNonNull();
        Class<?> dtoClass = queryDTO.getClass();
        // 获取当前类定义的属性（不包括父类）
        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (ignores.contains(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(queryDTO);
                if (value == null) {
                    continue;
                }
                buildWrapper(field, value);
            } catch (Exception ignored) {
                log.warn("反射异常，无法获取【{}】的值", field.getName());
            }
        }
    }

    private void buildWrapper (Field field, Object value) {
        String methodName = "where" + StringUtils.capitalize(field.getName());
        Class<?> wrapperClass = this.getClass();
        try {
            Method whereMethod = wrapperClass.getDeclaredMethod(methodName, field.getType());
            whereMethod.setAccessible(true);
            whereMethod.invoke(this, value);
        } catch (Exception ignored) {
            log.warn("反射异常，没有定义方法【{}】", methodName);
        }
    }

    private void requireNonNull() {
        if (queryDTO == null) {
            throw new PlusException(PlusExceptionEnum.NOT_INIT_DTO);
        }
    }
}
