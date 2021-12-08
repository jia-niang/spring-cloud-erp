package com.kabunx.erp.domain.bo;

import com.kabunx.erp.bo.QueryBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryBO<T> extends QueryBO<T> {
    String name;

    private void whereName(String name) {
        getWrapper().eq("name", name);
    }
}
