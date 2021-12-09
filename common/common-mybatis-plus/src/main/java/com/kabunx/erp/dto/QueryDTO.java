package com.kabunx.erp.dto;

import com.kabunx.erp.pojo.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDTO extends BaseDTO {
    private long page = 1L;
    private long pageSize = 20L;
}
