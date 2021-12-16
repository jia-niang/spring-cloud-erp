package com.kabunx.erp.domain;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义常用集合操作方法
 */
public class Collection<T> {
    private final List<T> items;

    public Collection(List<T> items) {
        this.items = items;
    }

    public List<T> get() {
        return items;
    }

    /**
     * 返回集合内某个元素的总数量
     */
    public int count() {
        return items.size();
    }

    /**
     * 返回集合中所有项目的平均值
     */
    public double avg(ToLongFunction<T> mapper) {
        OptionalDouble optionalDouble = items.stream().mapToLong(mapper).average();
        return optionalDouble.isPresent() ? optionalDouble.getAsDouble() : Double.NaN;
    }

    public long max(ToLongFunction<T> mapper) {
        OptionalLong optionalLong = items.stream().mapToLong(mapper).max();
        return optionalLong.isPresent() ? optionalLong.getAsLong() : 0;
    }

    public long min(ToLongFunction<T> mapper) {
        OptionalLong optionalLong = items.stream().mapToLong(mapper).min();
        return optionalLong.isPresent() ? optionalLong.getAsLong() : 0;
    }

    /**
     * 根据回调返回集合内所有项的和
     */
    public long sum(ToLongFunction<T> mapper) {
        return items.stream().mapToLong(mapper).sum();
    }

    /**
     * 传递一个闭包，来执行你的真值检验
     */
    public Boolean contains(Function<T, Boolean> filter) {
        for (T item : items) {
            if (filter.apply(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回集合中所有指定回调的集合项
     */
    public <C> Collection<C> only(Function<T, C> mapper) {
        return new Collection<>(
                items.stream().map(mapper).collect(Collectors.toList())
        );
    }

    /**
     * 返回除了指定回调以外的所有项目
     */
    public Collection<T> except(Predicate<T> filter) {
        return new Collection<>(
                items.stream().filter(filter).collect(Collectors.toList())
        );
    }

    /**
     * 获取集合中指定键对应的所有值
     */
    public <C> List<C> pluck(Function<T, C> mapper, Boolean notNull, Boolean unique) {
        Stream<C> newItems = items.stream().map(mapper);
        if (notNull) {
            newItems = newItems.filter(Objects::nonNull);
        }
        if (unique) {
            newItems = newItems.distinct();
        }
        return newItems.collect(Collectors.toList());
    }

    /**
     * 获取集合中指定键对应的所有值
     */
    public <C> List<C> pluck(Function<T, C> mapper) {
        return pluck(mapper, true, true);
    }


    /**
     * 通过指定的回调对集合元素进行分组
     */
    public <C> Map<C, List<T>> groupBy(Function<T, C> filter) {
        return items.stream().collect(Collectors.groupingBy(filter));
    }

    public void each(Consumer<T> action) {
        items.forEach(action);
    }

    /**
     *
     */
    public <C> Collection<C> map(Function<T, C> mapper) {
        return new Collection<>(items.stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * 根据回调过滤并返回一个新的集合
     */
    public Collection<T> filter(Predicate<T> predicate) {
        List<T> newList = items.stream().filter(predicate).collect(Collectors.toList());
        return new Collection<>(newList);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
