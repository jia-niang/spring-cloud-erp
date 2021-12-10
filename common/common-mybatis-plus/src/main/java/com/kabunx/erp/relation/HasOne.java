package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;
import java.util.List;

public class HasOne<TC, TP> extends Relation<TC, TP> {

    private final String foreignKey;

    private final String localKey;

    public HasOne(BaseMapper<TC> mapper, BaseMapper<TP> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    public void initEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        Collection<?> collection = getCollectionByKey(records, localKey);
        wrapper.in(foreignKey, collection);
        this.setEagerData(getMapper().selectList(wrapper));
    }

    @Override
    public void initRelation(List<TP> records) {
        initEagerData(records);
        for (TP record : records) {
            // 从EagerData获取record对应关系数据
            Object child = getValueByName(record, localKey);
            setValueByName(record, "xxx", child);
        }
    }
}
