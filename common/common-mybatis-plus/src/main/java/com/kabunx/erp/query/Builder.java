package com.kabunx.erp.query;

import com.kabunx.erp.exception.PlusException;
import com.kabunx.erp.exception.PlusExceptionEnum;
import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.SimplePaginator;
import com.kabunx.erp.relation.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Builder<T, Children extends Builder<T, Children>> {

    /**
     * 占位符
     */
    protected final Children children = (Children) this;

    protected PlusMapper<T> mapper;

    protected PlusWrapper<T> wrapper;

    /**
     * 查询offset
     */
    protected Integer offsetNum;

    /**
     * 查询limit
     */
    protected Integer limitNum;

    /**
     * 排序
     */
    protected HashMap<String, String> orders = new HashMap<>();

    /**
     * 所有被定义的关系
     */
    protected HashMap<String, Relation<?, T, ?>> relations = new HashMap<>();

    /**
     * 需要被加载的关系数据
     */
    protected ArrayList<String> loadRelations = new ArrayList<>();

    /**
     * 解决注解注入
     */
    public PlusMapper<T> getMapper() {
        return mapper;
    }

    /**
     * 初始化一个wrapper对象
     */
    public PlusWrapper<T> initPlusWrapper() {
        return new PlusWrapper<>();
    }

    public PlusWrapper<T> getWrapper() {
        if (null == wrapper) {
            wrapper = initPlusWrapper();
        }
        return wrapper;
    }

    public Children setWrapper(PlusWrapper<T> wrapper) {
        this.wrapper = wrapper;
        return children;
    }

    public Children select(String... columns) {
        getWrapper().select(columns);
        return children;
    }

    public Children filter(Consumer<PlusWrapper<T>> consumer) {
        return wrapper(consumer);
    }

    public Children wrapper(Consumer<PlusWrapper<T>> consumer) {
        consumer.accept(getWrapper());
        return children;
    }

    public Children orderByAsc(List<String> columns) {
        for (String column : columns) {
            orders.put(column, "asc");
        }
        return children;
    }

    public Children orderByAsc(String... columns) {
        return orderByAsc(Arrays.asList(columns));
    }

    public Children orderByDesc(List<String> columns) {
        for (String column : columns) {
            orders.put(column, "desc");
        }
        return children;
    }

    public Children orderByDesc(String... columns) {
        return orderByDesc(Arrays.asList(columns));
    }

    public Children offset(int value) {
        offsetNum = value;
        return children;
    }

    public Children skip(int value) {
        return offset(value);
    }

    public Children limit(int value) {
        limitNum = value;
        return children;
    }

    public Children forPage(int page, int perPage) {
        return offset((page - 1) * perPage).limit(perPage);
    }

    public Children forPageAfterId(int page, int perPage) {
        clearExistingOrders();
        return orderByDesc("id").forPage(page, perPage);
    }

    /**
     * 在分块时，对每个结果map处理
     */
    public <R> List<R> chunkMap(Function<T, R> callback) {
        List<R> results = new ArrayList<>();
        chunk(1000, items -> {
            results.addAll(
                    items.stream().map(callback).collect(Collectors.toList())
            );
            return true;
        });
        return results;
    }

    public boolean chunkById(Integer count, Function<List<T>, Boolean> callback) {
        return chunk(count, callback, true);
    }

    public boolean chunk(Integer count, Function<List<T>, Boolean> callback) {
        return chunk(count, callback, false);
    }

    /**
     * 将查询结果分成块。减少内存占用
     */
    public boolean chunk(Integer count, Function<List<T>, Boolean> callback, Boolean afterId) {
        int stageCount;
        List<T> results;
        int page = 1;
        do {
            results = afterId
                    ? forPageAfterId(page, count).get(false)
                    : forPage(page, count).get(false);
            stageCount = results.size();
            if (stageCount == 0) {
                break;
            }
            boolean result = callback.apply(results);
            if (!result) {
                return false;
            }
            results.clear();
            page++;
        } while (stageCount == count);
        return true;
    }

    /**
     * 获取结果集
     * 将对排序和limit做统一的处理
     */
    public List<T> get() {
        return get(true);
    }

    public List<T> get(Boolean clearWrapper) {
        PlusWrapper<T> wrapper = getWrapper();
        if (!orders.isEmpty()) {
            orders.forEach((key, value) -> {
                if ("desc".equals(value)) {
                    wrapper.orderByDesc(key);
                } else {
                    wrapper.orderByAsc(key);
                }
            });
        }
        if (null != limitNum) {
            if (null != offsetNum) {
                wrapper.last("LIMIT " + offsetNum + "," + limitNum);
            } else {
                wrapper.last("LIMIT " + limitNum);
            }
        }
        List<T> records = getMapper().selectList(wrapper);
        // 加载被定义的关系数据
        eagerLoadRelationsData(records);
        clear(clearWrapper);
        return records;
    }

    public List<T> get(String... columns) {
        return select(columns).get();
    }

    public Long count() {
        return getMapper().selectCount(getWrapper());
    }

    public T find(Serializable id) {
        return getMapper().selectById(id);
    }

    public T findOrFail(Serializable id) {
        T result = find(id);
        if (null == result) {
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
        if (null == result) {
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

    /**
     * 清除已有的排序
     */
    public void clearExistingOrders() {
        orders.clear();
    }

    /**
     * 清理分页数据
     */
    public void clear(Boolean clearWrapper) {
        offsetNum = null;
        limitNum = null;
        loadRelations.clear();
        clearExistingOrders();
        if (clearWrapper) {
            wrapper = null;
        }
    }

    /**
     * 被定义后才会加载关系数据
     */
    public Children with(String... relations) {
        List<String> rls = Arrays.asList(relations);
        this.loadRelations.removeAll(rls);
        this.loadRelations.addAll(rls);
        return children;
    }

    /**
     * 预定义向关系中添加其他添加
     */
    public <TC> void setRelation(String name, Relation<TC, T, ?> relation) {
        // 直接被替换，还是抛出异常
        if (!relations.containsKey(name)) {
            relations.put(name, relation);
        }
    }

    /**
     * 一对一关系
     */
    public <TC> Children hasOne(String name, Consumer<HasOne<TC, T>> callback) {
        HasOne<TC, T> oneRelation = new HasOne<>(getMapper());
        setRelation(name, oneRelation);
        callback.accept(oneRelation);
        return children;
    }

    /**
     * 一对多关系
     */
    public <TC> Children hasMany(String name, Consumer<HasMany<TC, T>> callback) {
        HasMany<TC, T> manyRelation = new HasMany<>(getMapper());
        setRelation(name, manyRelation);
        callback.accept(manyRelation);
        return children;
    }

    /**
     * 一对多反关系
     */
    public <TC> Children belongsTo(String name, Consumer<BelongsTo<TC, T>> callback) {
        BelongsTo<TC, T> belongsToRelation = new BelongsTo<>(getMapper());
        setRelation(name, belongsToRelation);
        callback.accept(belongsToRelation);
        return children;
    }

    /**
     * 多对多关系
     */
    public <TC> Children belongsToMany(String name, Consumer<BelongsToMany<TC, T>> callback) {
        BelongsToMany<TC, T> belongsToManyRelation = new BelongsToMany<>(getMapper());
        setRelation(name, belongsToManyRelation);
        callback.accept(belongsToManyRelation);
        return children;
    }

    /**
     * 加载关联数据
     */
    private void eagerLoadRelationsData(List<T> records) {
        for (String key : loadRelations) {
            if (relations.containsKey(key)) {
                Relation<?, T, ?> relation = relations.get(key);
                relation.handleRelation(records);
            }
        }
    }
}