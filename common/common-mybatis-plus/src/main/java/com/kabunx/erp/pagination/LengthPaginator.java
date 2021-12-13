package com.kabunx.erp.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LengthPaginator<T> implements Serializable {
    private List<T> list;
    private long total;
    private long current;
}
