package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public class HasOne<T, E> extends Relation<T, E> {

    private final String foreignKey;

    private final String localKey;

    public HasOne(BaseMapper<E> mapper, BaseMapper<T> parent, String foreignKey, String localKey) {
        super(mapper, parent);
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }


    public List<E> getEagerData(List<T> records) {
        QueryWrapper<E> wrapper = new QueryWrapper<>();
        wrapper.in(foreignKey, getCollectionByKey(records, localKey));
        return getMapper().selectList(wrapper);
    }

    @Override
    public void initRelation(List<T> records) {
        for (T record : records) {
            // 判断是否存在关系属性，否则没有意义，可以前置处理
            Class<?> recordClass = record.getClass();
            E child = getDefaultFor(record);

        }
    }

    private E getDefaultFor(T record) {
        return null;
    }
}
