package com.kabunx.erp.resource;

import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.Resource;
import com.kabunx.erp.pagination.SimplePaginator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SimplePaginatedResource<T> extends Resource {
    private List<T> list;
    private Boolean hasMore;

    public static <T> SimplePaginatedResource<T> toResource(SimplePaginator<?> paginator, Class<T> resource) {
        List<T> list = new ArrayList<>();
        paginator.getList().forEach(target -> list.add(Hydrate.map2Target(target, resource)));
        return assemble(list, paginator.getHasMore());
    }

    private static <T> SimplePaginatedResource<T> assemble(List<T> data, Boolean hasMore) {
        SimplePaginatedResource<T> resource = new SimplePaginatedResource<>();
        resource.setList(data);
        resource.setHasMore(hasMore);
        return resource;
    }
}
