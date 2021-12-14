package com.kabunx.erp.resource;

import com.kabunx.erp.converter.Hydrate;
import com.kabunx.erp.domain.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CollectionResource<T> extends Resource {

    public static <T> List<T> toResource(List<?> records, Class<T> resource) {
        List<T> list = new ArrayList<>();
        records.forEach(record -> list.add(Hydrate.map2Target(record, resource)));
        return list;
    }

    public static <T> List<T> toResource(Collection<?> records, Class<T> resource) {
        List<T> list = new ArrayList<>();
        records.forEach(record -> list.add(Hydrate.map2Target(record, resource)));
        return list;
    }
}
