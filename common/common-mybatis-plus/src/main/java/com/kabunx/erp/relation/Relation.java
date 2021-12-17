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
     * 用户自定义关联模型数据分组
     */
    protected Function<TC, Object> relatedGroupingBy;

    /**
     * 关联wrapper
     */
    protected PlusWrapper<TC> relatedWrapper;

    /**
     * 关联数据
     */
    protected Map<Object, List<TC>> relatedData = new HashMap<>();

    public Relation() {
        super();
    }

    public Relation(PlusMapper<TP> parent) {
        this.parentMapper = parent;
    }

    public Relation(PlusMapper<TC> related, PlusMapper<TP> parent) {
        this.relatedMapper = related;
        this.parentMapper = parent;
    }

    public void setRelatedArgs(PlusMapper<TC> relatedMapper, String foreignKey) {
        setRelatedArgs(relatedMapper, foreignKey, "id");
    }

    public void setRelatedArgs(PlusMapper<TC> relatedMapper, String foreignKey, String localKey) {
        this.relatedMapper = relatedMapper;
        this.foreignKey = foreignKey;
        this.localKey = localKey;
    }

    /**
     * 处理数据对应的关联数据，并销毁过程数据
     */
    public void handleRelation(List<TP> records) {
        initRelation(records);
        clear();
    }

    public abstract void initRelation(List<TP> records);

    /**
     * wrapper的别名
     */
    public void filter(Consumer<PlusWrapper<TC>> callback) {
        wrapper(callback);
    }

    /**
     * 添加额外过滤
     */
    public Children wrapper(Consumer<PlusWrapper<TC>> callback) {
        callback.accept(getRelatedWrapper());
        return children;
    }

    /**
     * 清理过程对象
     */
    public void clear() {
        relatedWrapper = null;
        relatedData.clear();
    }

    /**
     * 必须通过该方法获取Wrapper实例
     */
    protected PlusWrapper<TC> getRelatedWrapper() {
        if (relatedWrapper == null) {
            relatedWrapper = new PlusWrapper<>();
        }
        return relatedWrapper;
    }

    /**
     * 初始化关系数据
     */
    protected void initRelatedData(List<TP> records) {
        initRelatedData(records, localKey, foreignKey);
    }

    /**
     * 初始化关系数据，已根据关联主键分组
     */
    protected void initRelatedData(List<TP> records, String ownerKey, String relatedKey) {
        List<Object> collection = pluckByKey(records, ownerKey);
        if (!collection.isEmpty()) {
            PlusWrapper<TC> wrapper = getRelatedWrapper();
            wrapper.in(relatedKey, collection);
            List<TC> results = relatedMapper.selectList(wrapper);
            relatedData = groupRelatedData(results, relatedKey);
        }
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
     * 获取关联数据
     */
    protected TC getOneRelatedValue(TP record, String ownerKey) {
        List<TC> values = getManyRelatedValues(record, ownerKey);
        return values == null ? null : values.get(0);
    }

    protected List<TC> getManyRelatedValues(TP record, String ownerKey) {
        Object key;
        if (localCollect != null) {
            key = localCollect.apply(record);
        } else {
            key = getDeclaredFieldValue(record, ownerKey);
        }
        if (!relatedData.containsKey(key)) {
            return null;
        }
        return relatedData.get(key);
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

    protected Boolean requiredRelatedArgs() {
        return relatedMapper != null && foreignKey != null;
    }

    /**
     * 备用方法
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
}
