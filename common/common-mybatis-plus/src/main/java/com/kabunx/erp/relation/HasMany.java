package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.extension.mapper.PlusMapper;
import lombok.Setter;

import java.util.List;
import java.util.function.BiConsumer;

public class HasMany<TC, TP> extends Relation<TC, TP> {
    private final String foreignKey;

    private final String localKey;

    @Setter
    private BiConsumer<TP, List<TC>> callback;

    public HasMany(PlusMapper<TC> mapper, PlusMapper<TP> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    @Override
    public void initRelation(List<TP> records) {
        initEagerData(records);
        for (TP record : records) {
            // 从EagerData获取record对应关系数据
            List<TC> data = getRelationValue(getDeclaredFieldValue(record, localKey));
            if (callback != null) {
                callback.accept(record, data);
            }
        }
    }

    private void initEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        wrapper.in(foreignKey, getCollectionByKey(records, localKey));
        List<TC> results = mapper.selectList(wrapper);
        eagerData = buildEagerData(results, foreignKey);
    }

    private List<TC> getRelationValue(Object key) {
        if (!eagerData.containsKey(key)) {
            return null;
        }
        return eagerData.get(key);
    }
}
