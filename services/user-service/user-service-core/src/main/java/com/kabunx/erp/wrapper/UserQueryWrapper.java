package com.kabunx.erp.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class UserQueryWrapper<T> extends QueryWrapper<T> {

    /**
     * 该函数可以被指定到query-name的参数查询构造上
     */
    public void whereName(String value) {
        this.eq("name", value);
    }

    public void whereSex(Integer value) {
        this.eq("sex", value);
    }

    public void eqAccount(String account) {
        this.eq("account", account);
    }

    public void eqPhone(String phone) {
        this.eq("phone", phone);
    }
}
