package com.kabunx.erp.extension.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kabunx.erp.exception.DBException;
import com.kabunx.erp.exception.DBExceptionEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlusMapper<T> extends BaseMapper<T> {

    T sole(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

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
