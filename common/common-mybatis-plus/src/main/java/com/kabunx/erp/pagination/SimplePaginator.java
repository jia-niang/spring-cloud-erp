package com.kabunx.erp.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplePaginator<T> implements Serializable {
    private List<T> list;
    private Boolean hasMore;
}
