package com.kabunx.erp.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.pojo.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDTO<T> extends BaseDTO {
    private long page = 1L;
    private long pageSize = 10L;

    public Page<T> getPage() {
        return new Page<>(page, pageSize);
    }

    public QueryWrapper<T> getWrapper() {
        return new QueryWrapper<>();
    }
}
