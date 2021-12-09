package com.kabunx.erp.dto;

import com.kabunx.erp.pojo.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDTO extends BaseDTO {
    private long page = 1L;

    @Max(value = 200, message = "最多支持200条数据查询")
    private long pageSize = 20L;
}
