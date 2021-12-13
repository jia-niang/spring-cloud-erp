package com.kabunx.erp.relation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kabunx.erp.extension.mapper.PlusMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
abstract class Relation<TC, TP> {

    @Getter
    private final PlusMapper<TC> mapper;

    @Getter
    private final PlusMapper<TP> parent;

    private List<TC> eagerData = new ArrayList<>();

    public Relation(PlusMapper<TC> mapper, PlusMapper<TP> parent) {
        this.mapper = mapper;
        this.parent = parent;
    }

    abstract public void initRelation(List<TP> records);

    public List<TC> getEagerData() {
        return eagerData;
    }

    public void setEagerData(List<TC> eagerData) {
        this.eagerData = eagerData;
    }

    /**
     * 反射机制获取属性值
     */
    public Object getValueByName(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            log.warn("属性【{}】获取值失败", fieldName);
            return null;
        }
    }

    /**
     * 通过反射将值添加到对应属性上
     */
    public void setValueByName(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception ignored) {
            log.warn("属性【{}】赋值失败", fieldName);
        }
    }

    protected List<Object> getCollectionByKey(List<TP> records, String key) {
        return records.stream().map(record -> getValueByName(record, key)).collect(Collectors.toList());
    }
}
