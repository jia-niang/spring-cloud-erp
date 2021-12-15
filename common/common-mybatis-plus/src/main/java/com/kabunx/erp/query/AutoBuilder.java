package com.kabunx.erp.query;

import com.kabunx.erp.extension.mapper.PlusMapper;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import com.kabunx.erp.pagination.LengthPaginator;
import com.kabunx.erp.pagination.SimplePaginator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 构造器，将前端参数自定映射到自定义wrapper查询
 * 可用于简单的前端传参处理
 */
@Slf4j
public class AutoBuilder<T> extends Builder<T> {

    public AutoBuilder(PlusMapper<T> mapper, PlusWrapper<T> wrapper) {
        super(mapper);
        this.wrapper = wrapper;
    }

    public LengthPaginator<T> paginate() {
        wrapper.build();
        return paginate(wrapper.getPage(), wrapper.getPageSize());
    }

    public SimplePaginator<T> simplePaginate() {
        wrapper.build();
        return simplePaginate(wrapper.getPage(), wrapper.getPageSize());
    }

    @Override
    public List<T> get() {
        wrapper.build();
        return super.get();
    }
}