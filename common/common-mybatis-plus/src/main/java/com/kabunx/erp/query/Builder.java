package com.kabunx.erp.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kabunx.erp.exception.DBException;
import com.kabunx.erp.exception.DBExceptionEnum;
import com.kabunx.erp.extension.mapper.PlusMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Builder<T> {

    private final PlusMapper<T> plusMapper;

    private final QueryWrapper<T> queryWrapper;

    // 关系
    private final HashMap<String, List<BaseMapper<?>>> relations = new HashMap<>();

    public Builder(PlusMapper<T> plusMapper) {
        this.plusMapper = plusMapper;
        this.queryWrapper = new QueryWrapper<>();
    }

    public Builder<T> where(String column, Object value) {
        return where(column, "eq", value);
    }

    public Builder<T> where(String column, String operator, Object value) {
        switch (operator) {
            case "eq":
                queryWrapper.eq(column, value);
                break;
            case "like":
                queryWrapper.like(column, value);
                break;
        }
        return this;
    }

    public Builder<T> offset(int value) {
        return this;
    }

    public Builder<T> limit(int value) {
        queryWrapper.last("limit " + value);
        return this;
    }

    public List<T> get() {
        return plusMapper.selectList(queryWrapper);
    }

    public List<T> get(String... columns) {
        queryWrapper.select(columns);
        return get();
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
        queryWrapper.orderByDesc($column);
        return first();
    }

    public T sole() {
        List<T> records = this.limit(2).get();
        if (records.isEmpty()) {
            throw new DBException(DBExceptionEnum.NOT_FOUND);
        }
        if (records.size() > 1) {
            throw new DBException(DBExceptionEnum.MULTIPLE_RECORDS);
        }
        return records.get(0);
    }

    public void paginate() {
    }

    public Builder<T> withOne(BaseMapper<?> mapper, String foreignKey, String localKey) {
        List<BaseMapper<?>> mappers = relations.get("one");
        if (mappers.isEmpty()) {
            mappers = new ArrayList<>();
        }
        mappers.add(mapper);
        relations.put("one", mappers);
        return this;
    }

    public Builder<T> withMany(BaseMapper<?> mapper) {
        return this;
    }

    private void eagerLoadRelations(List<T> records) {
        if (records.size() > 0) {
            for (String key : relations.keySet()) {
                List<BaseMapper<?>> mappers = relations.get(key);
                for (BaseMapper<?> mapper : mappers) {
                    this.eagerLoadRelation(records, mapper);
                }

            }
        }
    }

    private void eagerLoadRelation(List<T> records, BaseMapper<?> mapper) {

    }


}
