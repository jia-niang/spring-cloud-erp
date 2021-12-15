package com.kabunx.erp.relation;

import com.kabunx.erp.extension.mapper.PlusMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class HasOne<TC, TP> extends Relation<TC, TP> {

    private final static String name = "hasOne";

    /**
     * 整合数据，将关联数据绑定到主数据
     */
    private BiConsumer<TP, TC> integrate;

    public HasOne() {
        super();
    }

    public HasOne(PlusMapper<TP> parent) {
        super(parent);
    }

    @Override
    public void initRelation(List<TP> records) {
        if (!requiredConditions()) {
            return;
        }
        initRelatedData(records);
        for (TP record : records) {
            TC relationValue = getRelationValue(getDeclaredFieldValue(record, localKey));
            if (integrate != null) {
                integrate.accept(record, relationValue);
            }
        }
    }

    /**
     * 获取关联数据
     */
    private TC getRelationValue(Object key) {
        if (!relatedData.containsKey(key)) {
            return null;
        }
        return relatedData.get(key).get(0);
    }

    private Boolean requiredConditions() {
        return requiredRelatedArgs() && integrate != null;
    }
}
