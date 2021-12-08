package com.kabunx.erp.domain.bo;

import com.kabunx.erp.bo.QueryBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryBO<T> extends QueryBO<T> {
    String name;
    Integer sex = 1;

    private void whereName(String value) {
        getQueryWrapper().eq("name", value);
    }

    private void whereSex(Integer value) {
        getQueryWrapper().eq("sex", value);
    }
}
