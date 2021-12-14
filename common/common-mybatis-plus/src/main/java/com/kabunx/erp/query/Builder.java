package com.kabunx.erp.query;

import com.kabunx.erp.exception.PlusException;
import com.kabunx.erp.exception.PlusExceptionEnum;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.SimplePaginator;
import com.kabunx.erp.relation.HasMany;
import com.kabunx.erp.relation.HasOne;
import com.kabunx.erp.relation.Relation;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class Builder<T> {

    protected final PlusMapper<T> mapper;

    @Setter
    protected PlusWrapper<T> wrapper;

    @Setter
    protected Integer offsetNum;

    @Setter
    protected Integer limitNum;

    /**
     * 所有被定义的关系
     */
    protected HashMap<String, Relation<?, T>> relations = new HashMap<>();

    /**
     * 需要被加载的关系数据
     */
    protected ArrayList<String> loadRelations = new ArrayList<>();

    @Setter
    protected ArrayList<HasOne<?, T>> oneRelations = new ArrayList<>();

    @Setter
    protected ArrayList<HasMany<?, T>> manyRelations = new ArrayList<>();

    public Builder(PlusMapper<T> mapper) {
        this.mapper = mapper;
        this.wrapper = new PlusWrapper<>();
    }

    public Builder(PlusMapper<T> mapper, PlusWrapper<T> wrapper) {
        this.mapper = mapper;
        this.wrapper = wrapper;
    }

    public Builder<T> select(String... columns) {
        wrapper.select(columns);
        return this;
    }

    public Builder<T> filter(Consumer<PlusWrapper<T>> consumer) {
        return wrapper(consumer);
    }

    public Builder<T> wrapper(Consumer<PlusWrapper<T>> consumer) {
        consumer.accept(wrapper);
        return this;
    }

    public Builder<T> orderByAsc(List<String> columns) {
        wrapper.orderByAsc(columns);
        return this;
    }

    public Builder<T> orderByAsc(String... columns) {
        return orderByAsc(Arrays.asList(columns));
    }

    public Builder<T> orderByDesc(List<String> columns) {
        wrapper.orderByDesc(columns);
        return this;
    }

    public Builder<T> orderByDesc(String... columns) {
        return orderByDesc(Arrays.asList(columns));
    }

    public Builder<T> offset(int value) {
        offsetNum = value;
        return this;
    }

    public Builder<T> skip(int value) {
        return offset(value);
    }

    public Builder<T> limit(int value) {
        limitNum = value;
        return this;
    }

    public Builder<T> forPage(int page, int perPage) {
        return offset((page - 1) * perPage).limit(perPage);
    }

    /**
     * 被定义后才会加载关系数据
     */
    public Builder<T> with(String... relations) {
        this.loadRelations.addAll(Arrays.asList(relations));
        return this;
    }

    /**
     * 获取结果集
     */
    public List<T> get() {
        String lastLimit = "";
        if (limitNum != null) {
            if (offsetNum != null) {
                lastLimit = "LIMIT " + offsetNum + "," + limitNum;
            } else {
                lastLimit = "LIMIT " + limitNum;
            }
        }
        if (!lastLimit.isEmpty()) {
            wrapper.last(lastLimit);
        }
        List<T> records = mapper.selectList(wrapper);
        // 加载被定义的关系数据
        eagerLoadRelationsData(records);
        return records;
    }

    public List<T> get(String... columns) {
        return select(columns).get();
    }

    public Long count() {
        return mapper.selectCount(wrapper);
    }

    public T find(Serializable id) {
        return mapper.selectById(id);
    }

    public T findOrFail(Serializable id) {
        T result = find(id);
        if (result == null) {
            throw new PlusException(PlusExceptionEnum.NOT_FOUND);
        }
        return result;
    }

    public T first() {
        List<T> records = limit(1).get();
        if (records.isEmpty()) {
            return null;
        }
        return records.get(0);
    }

    public T firstOrFail() {
        T result = first();
        if (result == null) {
            throw new PlusException(PlusExceptionEnum.NOT_FOUND);
        }
        return result;
    }

    public T latest(String $column) {
        return orderByDesc($column).first();
    }

    /**
     * 获取唯一数据
     */
    public T sole() {
        List<T> records = limit(2).get();
        if (records.isEmpty()) {
            throw new PlusException(PlusExceptionEnum.NOT_FOUND);
        }
        if (records.size() > 1) {
            throw new PlusException(PlusExceptionEnum.MULTIPLE_RECORDS);
        }
        return records.get(0);
    }

    /**
     * 包含简单分页参数的分页信息
     */
    public LengthPaginator<T> paginate(int page, int perPage) {
        // 1、获取总数
        Long count = count();
        // 2、查询结果集
        List<T> result = new ArrayList<>();
        if (count > 0) {
            result = forPage(page, perPage).get();
        }
        // 3、构造分页
        return new LengthPaginator<>(result, count, page);
    }

    /**
     * 简单的分页
     * 推荐在“加载更多中”使用
     */
    public SimplePaginator<T> simplePaginate(int page, int perPage) {
        // 向下多查询一条
        List<T> result = forPage(page, perPage + 1).get();
        // 获取真正查询的条数
        List<T> records = result.subList(0, perPage);
        return new SimplePaginator<>(records, result.size() > perPage);
    }

    // 为了向关系中添加其他添加
    public <TC> PlusWrapper<TC> setRelation(String name, Relation<TC, T> relation) {
        // 直接被替换，还是抛出异常
        relations.put(name, relation);
        return relation.wrapper();
    }

    public <TC> Builder<T> setRelation(String name, Relation<TC, T> relation, Consumer<PlusWrapper<TC>> callback) {
        relations.put(name, relation);
        callback.accept(relation.wrapper());
        return this;
    }

    public <TC> Builder<T> hasOne(PlusMapper<TC> mapper, String foreignKey, BiConsumer<T, TC> callback) {
        return hasOne(mapper, foreignKey, "id", callback);
    }

    public <TC> Builder<T> hasOne(PlusMapper<TC> mapper, String foreignKey, String localKey, BiConsumer<T, TC> callback) {
        HasOne<TC, T> hasOne = new HasOne<>(mapper, this.mapper, foreignKey, localKey);
        hasOne.setCallback(callback);
        oneRelations.add(hasOne);
        return this;
    }

    public <TC> Builder<T> hasMany(PlusMapper<TC> mapper, String foreignKey, BiConsumer<T, List<TC>> callback) {
        return hasMany(mapper, foreignKey, "id", callback);
    }

    public <TC> Builder<T> hasMany(PlusMapper<TC> mapper, String foreignKey, String localKey, BiConsumer<T, List<TC>> callback) {
        HasMany<TC, T> hasMany = new HasMany<>(mapper, this.mapper, foreignKey, localKey);
        hasMany.setCallback(callback);
        manyRelations.add(hasMany);
        return this;
    }

    public <TC> Builder<T> belongTo(PlusMapper<TC> mapper, String foreignKey, BiConsumer<T, List<TC>> callback) {
        return this;
    }

    public <TC> Builder<T> belongToMany(PlusMapper<TC> mapper, String foreignKey, BiConsumer<T, List<TC>> callback) {
        return this;
    }

    /**
     * 加载关联数据
     */
    private void eagerLoadRelationsData(List<T> records) {
        for (String with: loadRelations) {
            if (relations.containsKey(with)) {
                Relation<?, T> relation = relations.get(with);
                relation.initRelation(records);
            }
        }
    }
}