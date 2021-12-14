package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.extension.mapper.PlusMapper;
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

    private String foreignKey;

    private String localKey;

    private BiConsumer<TP, List<TC>> fullback;

    public HasMany() {
        super();
    }

    public HasMany(PlusMapper<TP> parent) {
        super(parent);
    }

    @Override
    public void initRelation(List<TP> records) {
        initEagerData(records);
        for (TP record : records) {
            // 从EagerData获取record对应关系数据
            List<TC> data = getRelationValue(getDeclaredFieldValue(record, localKey));
            if (fullback != null) {
                fullback.accept(record, data);
            }
        }
    }

    private void initEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        wrapper.in(foreignKey, getCollectionByKey(records, localKey));
        List<TC> results = relatedMapper.selectList(wrapper);
        eagerData = buildEagerData(results, foreignKey);
    }

    private List<TC> getRelationValue(Object key) {
        if (!eagerData.containsKey(key)) {
            return null;
        }
        return eagerData.get(key);
    }
}
