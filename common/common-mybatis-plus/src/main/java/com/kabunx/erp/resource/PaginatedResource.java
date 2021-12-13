package com.kabunx.erp.resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.Resource;
import com.kabunx.erp.pagination.LengthPaginator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaginatedResource<T> extends Resource {
    private List<T> list;
    private long total;
    private long current;

    public static <T> PaginatedResource<T> toResource(IPage<?> page, Class<T> resource) {
        List<T> list = new ArrayList<>();
        page.getRecords().forEach(target -> list.add(Hydrate.map(target, resource)));
        return assemble(list, page.getTotal(), page.getCurrent());
    }

    public static <T> PaginatedResource<T> toResource(LengthPaginator<?> paginator, Class<T> resource) {
        List<T> list = new ArrayList<>();
        paginator.getList().forEach(target -> list.add(Hydrate.map(target, resource)));
        return assemble(list, paginator.getTotal(), paginator.getCurrent());
    }

    private static <T> PaginatedResource<T> assemble(List<T> data, Long total, Long current) {
        PaginatedResource<T> paginatedResource = new PaginatedResource<>();
        paginatedResource.setList(data);
        paginatedResource.setTotal(total);
        paginatedResource.setCurrent(current);
        return paginatedResource;
    }

}
