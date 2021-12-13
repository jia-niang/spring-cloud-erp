package com.kabunx.erp.extension.query;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlusWrapper<T> extends AbstractWrapper<T, String, PlusWrapper<T>>
        implements Query<PlusWrapper<T>, T, String> {

    private final SharedString sqlSelect = new SharedString();

    public PlusWrapper() {
        this(null);
    }

    public PlusWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    public PlusWrapper(T entity, String... columns) {
        super.setEntity(entity);
        super.initNeed();
        this.select(columns);
    }

    public PlusWrapper(
            T entity,
            Class<T> entityClass,
            AtomicInteger paramNameSeq,
            Map<String, Object> paramNameValuePairs,
            MergeSegments mergeSegments,
            SharedString paramAlias,
            SharedString lastSql,
            SharedString sqlComment,
            SharedString sqlFirst
    ) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    @Override
    protected PlusWrapper<T> instance() {
        return new PlusWrapper<>(
                getEntity(),
                getEntityClass(),
                paramNameSeq,
                paramNameValuePairs,
                new MergeSegments(),
                paramAlias,
                SharedString.emptyString(),
                SharedString.emptyString(),
                SharedString.emptyString()
        );
    }

    @Override
    public PlusWrapper<T> select(String... columns) {
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(StringPool.COMMA, columns));
        }
        return typedThis;
    }

    @Override
    public PlusWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        super.setEntityClass(entityClass);
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(getEntityClass()).chooseSelect(predicate));
        return typedThis;
    }

    public PlusWrapper<T> where(Consumer<PlusWrapper<T>> consumer) {
        return and(consumer);
    }

    public PlusWrapper<T> orWhere(Consumer<PlusWrapper<T>> consumer) {
        return or(consumer);
    }
}
