package com.kabunx.erp.domain.dto;

import com.kabunx.erp.domain.FilterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserFilterDto<T> extends FilterDto<T> {
    private String name;
}
