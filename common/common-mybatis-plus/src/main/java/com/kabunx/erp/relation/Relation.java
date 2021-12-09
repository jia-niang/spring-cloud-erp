package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

abstract class Relation<T, E> {

    @Getter
    private final BaseMapper<E> mapper;

    @Getter
    private final BaseMapper<T> parent;

    public Relation(BaseMapper<E> mapper, BaseMapper<T> parent) {
        this.mapper = mapper;
        this.parent = parent;
    }

    abstract public void initRelation(List<T> records);

    protected Collection<?> getCollectionByKey(List<T> records, String key) {
        return null;
    }


}
