package com.kabunx.erp.bo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.pojo.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryBO<T> extends BaseDTO {
    private long page = 1L;
    private long pageSize = 10L;

    public Page<T> getPage() {
        return new Page<>(page, pageSize);
    }

    public QueryWrapper<T> getWrapper() {
        Class<? extends QueryBO> ss = this.getClass();
        Field[] fields = ss.getDeclaredFields();
        for (Field field : fields) {

        }

        return new QueryWrapper<>();
    }
}
