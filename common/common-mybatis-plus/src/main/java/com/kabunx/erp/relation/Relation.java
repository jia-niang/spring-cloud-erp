package com.kabunx.erp.relation;

import com.kabunx.erp.domain.Collection;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CaseUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Data
public abstract class Relation<TC, TP, Children extends Relation<TC, TP, Children>> {
    /**
     * 占位符
     */
    protected final Children children = (Children) this;

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
    protected String localKey;

    /**
     * 用户自定义当前模型数据合集，关联查询使用
     */
    protected Function<TP, Object> localCollect;

    /**
     * 关联数据
     */
    protected Map<Object, List<TC>> relatedData = new HashMap<>();

    /**
     * 用户自定义关联模型数据分组
     */
    protected Function<TC, Object> relatedGroupingBy;

    public Relation() {
        super();
    }

    public Relation(PlusMapper<TP> parent) {
        this.parentMapper = parent;
        this.relatedWrapper = initPlusWrapper();
    }

    public Relation(PlusMapper<TC> related, PlusMapper<TP> parent) {
        this.relatedMapper = related;
        this.parentMapper = parent;
        this.relatedWrapper = initPlusWrapper();
    }

    public PlusWrapper<TC> initPlusWrapper() {
        return new PlusWrapper<>();
    }

    public void setRelatedArgs(PlusMapper<TC> relatedMapper, String foreignKey) {
        setRelatedArgs(relatedMapper, foreignKey, "id");
    }

    public void setRelatedArgs(PlusMapper<TC> relatedMapper, String foreignKey, String localKey) {
        this.relatedMapper = relatedMapper;
        this.foreignKey = foreignKey;
        this.localKey = localKey;
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
    public Children wrapper(Consumer<PlusWrapper<TC>> callback) {
        callback.accept(relatedWrapper);
        return children;
    }

    /**
     * 获取有效键集合数据
     */
    public List<Object> pluckByKey(List<TP> records, String key) {
        return new Collection<>(records).pluck(r -> {
            if (localCollect != null) {
                return localCollect.apply(r);
            }
            return getDeclaredFieldValue(r, key);
        });
    }

    /**
     * 初始化关系数据
     */
    protected void initRelatedData(List<TP> records) {
        List<Object> collection = pluckByKey(records, localKey);
        if (!collection.isEmpty()) {
            PlusWrapper<TC> wrapper = newRelatedWrapper();
            wrapper.in(foreignKey, collection);
            List<TC> results = relatedMapper.selectList(wrapper);
            relatedData = groupRelatedData(results, foreignKey);
        }
    }

    /**
     * 构建分组数据
     */
    protected Map<Object, List<TC>> groupRelatedData(List<TC> results, String foreignKey) {
        return new Collection<>(results).groupBy(r -> {
            if (relatedGroupingBy != null) {
                return relatedGroupingBy.apply(r);
            }
            return getDeclaredFieldValue(r, foreignKey);
        });
    }

    protected PlusWrapper<TC> newRelatedWrapper() {
        if (relatedWrapper == null) {
            relatedWrapper = new PlusWrapper<>();
        }
        return relatedWrapper;
    }

    protected Boolean requiredRelatedArgs() {
        return relatedWrapper != null && foreignKey != null;
    }

    /**
     * 反射机制获取属性值
     */
    protected Object getDeclaredFieldValue(Object object, String fieldName) {
        // 我们约定bean的属性为驼峰模式
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
    protected void setDeclaredFieldValue(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception ignored) {
            log.warn("属性【{}】赋值失败", fieldName);
        }
    }
}
