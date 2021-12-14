package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kabunx.erp.extension.mapper.PlusMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class HasOne<TC, TP> extends Relation<TC, TP> {

    private final static String name = "hasOne";

    private BiConsumer<TP, TC> fullback;

    public HasOne() {
        super();
    }

    public HasOne(PlusMapper<TP> parent) {
        super(parent);
    }

    public void setRelated(PlusMapper<TC> relatedMapper, String foreignKey) {
        this.relatedMapper = relatedMapper;
        this.foreignKey = foreignKey;
    }

    @Override
    public void initRelation(List<TP> records) {
        initEagerData(records);
        for (TP record : records) {
            TC relationValue = getRelationValue(getDeclaredFieldValue(record, localKey));
            if (fullback != null) {
                fullback.accept(record, relationValue);
            }
        }
    }

    /**
     * 初始化关系数据
     */
    public void initEagerData(List<TP> records) {
        QueryWrapper<TC> wrapper = new QueryWrapper<>();
        Collection<?> collection = getCollectionByKey(records, localKey);
        if (!collection.isEmpty()) {
            wrapper.in(foreignKey, collection);
            List<TC> results = relatedMapper.selectList(wrapper);
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
