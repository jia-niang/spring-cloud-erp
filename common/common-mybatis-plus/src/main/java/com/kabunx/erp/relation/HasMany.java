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
public class HasMany<TC, TP> extends Relation<TC, TP, HasMany<TC, TP>> {
    private final static String name = "hasMany";

    /**
     * 自定义回调，关联数据回填到主属性的回调
     */
    private BiConsumer<TP, List<TC>> merge;

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
            List<TC> values = getManyRelatedValues(record, localKey);
            merge.accept(record, values);
        }
    }

    private Boolean requiredConditions() {
        return requiredRelatedArgs() && merge != null;
    }
}
