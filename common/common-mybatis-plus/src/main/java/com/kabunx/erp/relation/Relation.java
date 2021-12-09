package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kabunx.erp.query.Builder;

abstract class Relation<T> {
    private final BaseMapper<T> mapper;

    protected Relation(BaseMapper<T> mapper) {
        this.mapper = mapper;
    }
}
