package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

abstract class Relation<TC, TP> {

    @Getter
    private final BaseMapper<TC> mapper;

    @Getter
    private final BaseMapper<TP> parent;

    public Relation(BaseMapper<TC> mapper, BaseMapper<TP> parent) {
        this.mapper = mapper;
        this.parent = parent;
    }

    abstract public void initRelation(List<TP> records);

    protected Collection<?> getCollectionByKey(List<TP> records, String key) {
        return null;
    }


}
