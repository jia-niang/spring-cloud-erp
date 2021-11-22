package com.kabunx.erp.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabunx.erp.base.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilterDto<T> extends Dto {
    private long page = 1L;
    private long pageSize = 10L;

    public Page<T> getPage() {
        return new Page<>(page, pageSize);
    }

    public QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }
}
