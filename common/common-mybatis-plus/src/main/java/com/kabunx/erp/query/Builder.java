package com.kabunx.erp.query;

import com.kabunx.erp.exception.DBException;
import com.kabunx.erp.exception.DBExceptionEnum;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.query.PlusWrapper;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.Paginator;
import com.kabunx.erp.relation.HasMany;
import com.kabunx.erp.relation.HasOne;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class Builder<T> {

    private Integer offsetNum;

    private Integer limitNum;

    private final PlusMapper<T> plusMapper;

    private final PlusWrapper<T> wrapper;

    // 关系
    private final ArrayList<HasOne<?, T>> oneRelations = new ArrayList<>();

    private final ArrayList<HasMany<?, T>> manyRelations = new ArrayList<>();

    public Builder(PlusMapper<T> plusMapper) {
        this.plusMapper = plusMapper;
        this.wrapper = new PlusWrapper<>();
    }

    public Builder<T> wrapper(Consumer<PlusWrapper<T>> consumer) {
        consumer.accept(wrapper);
        return this;
    }

    public Builder<T> select(String... columns) {
        wrapper.select(columns);
        return this;
    }

    public Builder<T> where(Consumer<PlusWrapper<T>> consumer) {
        wrapper.and(consumer);
        return this;
    }

    public Builder<T> orWhere(Consumer<PlusWrapper<T>> consumer) {
        wrapper.or(consumer);
        return this;
    }

    public Builder<T> where(String column, Object value) {
        return where(column, "eq", value);
    }

    public Builder<T> whereIn(String column, Collection<?> values) {
        wrapper.in(column, values);
        return this;
    }

    public Builder<T> whereIn(String column, Object... values) {
        wrapper.in(column, values);
        return this;
    }

    public Builder<T> whereNotIn(String column, Collection<?> values) {
        wrapper.notIn(column, values);
        return this;
    }

    public Builder<T> whereNotIn(String column, Object... values) {
        wrapper.notIn(column, values);
        return this;
    }

    public Builder<T> whereNull(List<String> columns) {
        for (String column : columns) {
            wrapper.isNull(column);
        }
        return this;
    }

    public Builder<T> whereNull(String... columns) {
        for (String column : columns) {
            wrapper.isNull(column);
        }
        return this;
    }

    public Builder<T> whereNull(String column) {
        wrapper.isNull(column);
        return this;
    }

    public Builder<T> whereNotNull(String column) {
        wrapper.isNotNull(column);
        return this;
    }

    public Builder<T> whereBetween(String column, Object val1, Object val2) {
        wrapper.between(column, val1, val2);
        return this;
    }

    public Builder<T> whereBetween(String column, List<Object> values) {
        if (values.size() == 2) {
            wrapper.between(column, values.get(0), values.get(1));
        }
        return this;
    }

    public Builder<T> orWhereBetween(String column, Object val1, Object val2) {
        wrapper.or(wrapper -> wrapper.between(column, val1, val2));
        return this;
    }

    public Builder<T> whereNotBetween(String column, Object val1, Object val2) {
        wrapper.notBetween(column, val1, val2);
        return this;
    }

    public Builder<T> orWhereNotBetween(String column, Object val1, Object val2) {
        wrapper.or(wrapper -> wrapper.notBetween(column, val1, val2));
        return this;
    }

    public Builder<T> where(String column, String operator, Object value) {
        switch (operator) {
            case "eq":
                wrapper.eq(column, value);
                break;
            case "like":
                wrapper.like(column, value);
                break;
            case "leftLike":
                wrapper.likeLeft(column, value);
                break;
            case "rightLike":
                wrapper.likeRight(column, value);
                break;
        }
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
        List<T> records = plusMapper.selectList(wrapper);
        // 加载被定义的关系数据
        eagerLoadRelationsData(records);
        return records;
    }

    public List<T> get(String... columns) {
        return select(columns).get();
    }

    public Long count() {
        return plusMapper.selectCount(wrapper);
    }

    public T find(Serializable id) {
        return plusMapper.selectById(id);
    }

    public T findOrFail(Serializable id) {
        T result = find(id);
        if (result == null) {
            throw new DBException(DBExceptionEnum.NOT_FOUND);
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
            throw new DBException(DBExceptionEnum.NOT_FOUND);
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
            throw new DBException(DBExceptionEnum.NOT_FOUND);
        }
        if (records.size() > 1) {
            throw new DBException(DBExceptionEnum.MULTIPLE_RECORDS);
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
    public Paginator<T> simplePaginate(int page, int perPage) {
        // 向下多查询一条
        List<T> result = forPage(page, perPage + 1).get();
        // 获取真正查询的条数
        List<T> records = result.subList(0, perPage);
        return new Paginator<>(records, result.size() > perPage);
    }

    public <TC> Builder<T> withOne(PlusMapper<TC> mapper, String foreignKey, BiConsumer<T, TC> callback) {
        return withOne(mapper, foreignKey, "id", callback);
    }

    public <TC> Builder<T> withOne(PlusMapper<TC> mapper, String foreignKey, String localKey, BiConsumer<T, TC> callback) {
        HasOne<TC, T> hasOne = new HasOne<>(mapper, plusMapper, foreignKey, localKey);
        hasOne.setCallback(callback);
        oneRelations.add(hasOne);
        return this;
    }

    public <TC> Builder<T> withMany(PlusMapper<TC> mapper, String foreignKey, BiConsumer<T, List<TC>> callback) {
        return withMany(mapper, foreignKey, "id", callback);
    }

    public <TC> Builder<T> withMany(PlusMapper<TC> mapper, String foreignKey, String localKey, BiConsumer<T, List<TC>> callback) {
        HasMany<TC, T> hasMany = new HasMany<>(mapper, plusMapper, foreignKey, localKey);
        hasMany.setCallback(callback);
        manyRelations.add(hasMany);
        return this;
    }

    /**
     * 加载关联数据
     */
    private void eagerLoadRelationsData(List<T> records) {
        if (records.size() > 0) {
            for (HasOne<?, T> relation : oneRelations) {
                relation.initRelation(records);
            }
            for (HasMany<?, T> relation : manyRelations) {
                relation.initRelation(records);
            }
        }
    }
}