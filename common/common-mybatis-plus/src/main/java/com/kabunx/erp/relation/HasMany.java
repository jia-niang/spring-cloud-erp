package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.extension.mapper.PlusMapper;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class HasMany<TC, TP> extends Relation<TC, TP> {
    private final String foreignKey;

    private final String localKey;

    private BiConsumer<TP, List<TC>> backFill;

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
        initEagerData(records);
        for (TP record : records) {
            // 从EagerData获取record对应关系数据
            Object child = getValueByName(record, localKey);
            setValueByName(record, "xxx", child);
        }
    }

    private void initEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        Collection<?> collection = getCollectionByKey(records, localKey);
        wrapper.in(foreignKey, collection);
        // 分组
        this.setEagerData(getMapper().selectList(wrapper));
    }
}
