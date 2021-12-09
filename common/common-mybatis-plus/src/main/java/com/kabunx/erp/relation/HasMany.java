package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public class HasMany<T, E> extends Relation<T, E> {
    private final String foreignKey;

    private final String localKey;

    public HasMany(BaseMapper<E> mapper, BaseMapper<T> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    public void initRelation() {

    }

    @Override
    public void initRelation(List<T> records) {

    }
}
