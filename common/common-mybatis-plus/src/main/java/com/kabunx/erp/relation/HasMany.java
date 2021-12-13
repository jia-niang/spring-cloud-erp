package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.extension.mapper.PlusMapper;

import java.util.List;

public class HasMany<TC, TP> extends Relation<TC, TP> {
    private final String foreignKey;

    private final String localKey;

    public HasMany(PlusMapper<TC> mapper, PlusMapper<TP> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    public List<TC> getEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        wrapper.in(foreignKey, getCollectionByKey(records, localKey));
        return getMapper().selectList(wrapper);
    }

    @Override
    public void initRelation(List<TP> records) {

    }
}
