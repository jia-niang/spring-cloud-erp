package com.kabunx.erp.query;

import com.kabunx.erp.dto.QueryDTO;
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
public class AutoBuilder<T> extends Builder<T, AutoBuilder<T>> {

    private final QueryDTO queryDTO;

    public AutoBuilder(
            PlusMapper<T> mapper,
            PlusWrapper<T> wrapper,
            QueryDTO queryDTO
    ) {
        super(mapper);
        this.wrapper = wrapper;
        this.queryDTO = queryDTO;
    }

    public LengthPaginator<T> paginate() {
        wrapper.autoBuild(queryDTO);
        return paginate(queryDTO.getPage(), queryDTO.getPageSize());
    }

    public SimplePaginator<T> simplePaginate() {
        wrapper.autoBuild(queryDTO);
        return simplePaginate(queryDTO.getPage(), queryDTO.getPageSize());
    }

    @Override
    public List<T> get() {
        wrapper.autoBuild(queryDTO);
        return super.get();
    }
}