package com.kabunx.erp.relation;

import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CaseUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Data
public abstract class Relation<TC, TP> {
    protected PlusMapper<TP> parentMapper;

    protected PlusMapper<TC> relatedMapper;

    protected PlusWrapper<TC> relatedWrapper;

    /**
     * 自定义外间
     */
    protected String foreignKey;

    /**
     * 父表中想要作为关联关系的字段的名称，一般设置为主键
     */
    protected String localKey = "id";

    protected Map<Object, List<TC>> eagerData = new HashMap<>();

    public Relation() {
        initPlusWrapper();
    }

    public Relation(PlusMapper<TP> parent) {
        this.parentMapper = parent;
        initPlusWrapper();
    }

    public Relation(PlusMapper<TC> related, PlusMapper<TP> parent) {
        this.relatedMapper = related;
        this.parentMapper = parent;
        initPlusWrapper();
    }

    public void initPlusWrapper() {
        this.relatedWrapper = new PlusWrapper<>();
    }

    public void setRelated(PlusMapper<TC> relatedMapper, String foreignKey) {
        this.relatedMapper = relatedMapper;
        this.foreignKey = foreignKey;
    }

    public abstract void initRelation(List<TP> records);

    /**
     * 别名
     */
    public void filter(Consumer<PlusWrapper<TC>> callback) {
        callback.accept(relatedWrapper);
    }

    public PlusWrapper<TC> filter() {
        return relatedWrapper;
    }

    /**
     * 添加额外过滤
     */
    public Relation<TC, TP> wrapper(Consumer<PlusWrapper<TC>> callback) {
        callback.accept(relatedWrapper);
        return this;
    }

    /**
     * 获取有效键集合数据
     */
    public List<Object> getCollectionByKey(List<TP> records, String key) {
        return records.stream()
                .map(record -> getDeclaredFieldValue(record, key))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 构建分组数据
     */
    public Map<Object, List<TC>> buildEagerData(List<TC> results, String foreignKey) {
        return results.stream().collect(
                Collectors.groupingBy(r -> getDeclaredFieldValue(r, foreignKey))
        );
    }

    /**
     * 反射机制获取属性值
     */
    public Object getDeclaredFieldValue(Object object, String fieldName) {
        // 将被转化为驼峰
        fieldName = CaseUtils.toCamelCase(fieldName, false, '_');
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
     * 暂时用不到，
     * 目前可通过回调处理关系数据的绑
     */
    public void setDeclaredFieldValue(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception ignored) {
            log.warn("属性【{}】赋值失败", fieldName);
        }
    }
}
