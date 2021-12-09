package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public class HasOne<TC, TP> extends Relation<TC, TP> {

    private final String foreignKey;

    private final String localKey;

    public HasOne(BaseMapper<TC> mapper, BaseMapper<TP> parent, String foreignKey, String localKey) {
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
        for (TP record : records) {
            // 判断是否存在关系属性，否则没有意义，可以前置处理
            Class<?> recordClass = record.getClass();
            TC child = getDefaultFor(record);
        }
    }

    private TC getDefaultFor(TP record) {
        return null;
    }
}
