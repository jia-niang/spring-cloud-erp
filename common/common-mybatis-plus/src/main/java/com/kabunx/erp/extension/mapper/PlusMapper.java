package com.kabunx.erp.extension.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kabunx.erp.exception.DBException;
import com.kabunx.erp.exception.DBExceptionEnum;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface PlusMapper<T> extends BaseMapper<T> {

    default Class<T> getGenericClass() {
        // 强转成 参数化类型 实体
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        // 获取超类的泛型类型数组, 而我们要的是第一个参数
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    default T sole(@Param(Constants.WRAPPER) QueryWrapper<T> queryWrapper) {
        queryWrapper.last("limit 2");
        List<T> result = this.selectList(queryWrapper);
        if (result.isEmpty()) {
            throw new DBException(DBExceptionEnum.NOT_FOUND);
        }
        if (result.size() > 2) {
            throw new DBException(DBExceptionEnum.NOT_FOUND);
        }
        return result.get(0);
    }

    default T first(@Param(Constants.WRAPPER) QueryWrapper<T> queryWrapper) {
        queryWrapper.last("limit 1");
        List<T> result = this.selectList(queryWrapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    default T firstOrFail(@Param(Constants.WRAPPER) QueryWrapper<T> queryWrapper) {
        T result = this.first(queryWrapper);
        if (result == null) {
            throw new DBException(DBExceptionEnum.NOT_FOUND);
        }
        return result;
    }
}
