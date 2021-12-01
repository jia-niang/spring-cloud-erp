package com.kabunx.erp.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class UserWrapper<T> extends QueryWrapper<T> {

    public void eqPhone(String phone) {
        this.eq("phone", phone);
    }
}
