package com.kabunx.erp.relation;

import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class HasMany<TC, TP> extends Relation<TC, TP> {
    private final static String name = "hasMany";

    private BiConsumer<TP, List<TC>> integrate;

    public HasMany() {
        super();
    }

    public HasMany(PlusMapper<TP> parent) {
        super(parent);
    }

    @Override
    public void initRelation(List<TP> records) {
        if (!requiredConditions()) {
            return;
        }
        initRelatedData(records);
        for (TP record : records) {
            // 从EagerData获取record对应关系数据
            List<TC> data = getRelationValue(getDeclaredFieldValue(record, localKey));
            if (integrate != null) {
                integrate.accept(record, data);
            }
        }
    }

    @Override
    protected void initRelatedData(List<TP> records) {
        PlusWrapper<TC> wrapper = newRelatedWrapper();
        wrapper.in(foreignKey, getCollectionByKey(records, localKey));
        List<TC> results = relatedMapper.selectList(wrapper);
        relatedData = buildRelatedData(results, foreignKey);
    }

    private List<TC> getRelationValue(Object key) {
        if (!relatedData.containsKey(key)) {
            return null;
        }
        return relatedData.get(key);
    }

    private Boolean requiredConditions() {
        return requiredRelatedArgs() && integrate != null;
    }
}
