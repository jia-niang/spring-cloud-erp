package com.kabunx.erp.resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.Resource;
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
        PaginatedResource<T> paginatedResource = new PaginatedResource<>();
        List<T> list = new ArrayList<>();
        page.getRecords().forEach(record -> list.add(Hydrate.map(record, resource)));
        paginatedResource.setList(list);
        paginatedResource.setTotal(page.getTotal());
        paginatedResource.setCurrent(page.getCurrent());
        return paginatedResource;
    }

}
