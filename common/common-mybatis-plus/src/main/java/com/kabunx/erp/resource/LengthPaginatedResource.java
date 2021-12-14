package com.kabunx.erp.resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.Resource;
import com.kabunx.erp.pagination.LengthPaginator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LengthPaginatedResource<T> extends Resource {
    private List<T> list;
    private long total;
    private long current;

    public static <T> LengthPaginatedResource<T> toResource(IPage<?> page, Class<T> resource) {
        List<T> list = new ArrayList<>();
        page.getRecords().forEach(target -> list.add(Hydrate.map2Target(target, resource)));
        return assemble(list, page.getTotal(), page.getCurrent());
    }

    public static <T> LengthPaginatedResource<T> toResource(LengthPaginator<?> paginator, Class<T> resource) {
        List<T> list = new ArrayList<>();
        paginator.getList().forEach(target -> list.add(Hydrate.map2Target(target, resource)));
        return assemble(list, paginator.getTotal(), paginator.getCurrent());
    }

    private static <T> LengthPaginatedResource<T> assemble(List<T> data, Long total, Long current) {
        LengthPaginatedResource<T> lengthPaginatedResource = new LengthPaginatedResource<>();
        lengthPaginatedResource.setList(data);
        lengthPaginatedResource.setTotal(total);
        lengthPaginatedResource.setCurrent(current);
        return lengthPaginatedResource;
    }

}
