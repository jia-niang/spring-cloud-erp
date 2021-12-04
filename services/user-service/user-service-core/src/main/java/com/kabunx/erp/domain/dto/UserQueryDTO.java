package com.kabunx.erp.domain.dto;

import com.kabunx.erp.dto.QueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO<T> extends QueryDTO<T> {
    String name;
}
