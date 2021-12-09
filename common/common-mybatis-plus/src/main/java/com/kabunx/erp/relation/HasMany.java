package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public class HasMany<TC, TP> extends Relation<TC, TP> {
    private final String foreignKey;

    private final String localKey;

    public HasMany(BaseMapper<TC> mapper, BaseMapper<TP> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    public void initRelation() {

    }

    @Override
    public void initRelation(List<TP> records) {

    }
}
