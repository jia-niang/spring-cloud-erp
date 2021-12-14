package com.kabunx.erp.extension.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kabunx.erp.exception.PlusException;
import com.kabunx.erp.exception.PlusExceptionEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlusMapper<T> extends BaseMapper<T> {

    default T sole(@Param(Constants.WRAPPER) QueryWrapper<T> queryWrapper) {
        queryWrapper.last("limit 2");
        List<T> result = this.selectList(queryWrapper);
        if (result.isEmpty()) {
            throw new PlusException(PlusExceptionEnum.NOT_FOUND);
        }
        if (result.size() > 2) {
            throw new PlusException(PlusExceptionEnum.NOT_FOUND);
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
            throw new PlusException(PlusExceptionEnum.NOT_FOUND);
        }
        return result;
    }
}
