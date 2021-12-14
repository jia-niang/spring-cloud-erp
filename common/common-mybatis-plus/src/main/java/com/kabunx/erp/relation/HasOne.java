package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.extension.mapper.PlusMapper;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class HasOne<TC, TP> extends Relation<TC, TP> {

    private final String foreignKey;

    private final String localKey;

    @Setter
    private BiConsumer<TP, TC> callback;

    public HasOne(PlusMapper<TC> mapper, PlusMapper<TP> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    @Override
    public void initRelation(List<TP> records) {
        initEagerData(records);
        for (TP record: records) {
            TC relationValue = getRelationValue(getDeclaredFieldValue(record, localKey));
            if (callback != null) {
                callback.accept(record, relationValue);
            }
        }
    }

    /**
     * 初始化关系数据
     */
    public void initEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        Collection<?> collection = getCollectionByKey(records, localKey);
        if(!collection.isEmpty()) {
            wrapper.in(foreignKey, collection);
            List<TC> results = mapper.selectList(wrapper);
            eagerData = buildEagerData(results, foreignKey);
        }
    }

    /**
     * 获取关联数据
     */
    private TC getRelationValue(Object key) {
        if (!eagerData.containsKey(key)) {
            return null;
        }
        return eagerData.get(key).get(0);
    }
}
