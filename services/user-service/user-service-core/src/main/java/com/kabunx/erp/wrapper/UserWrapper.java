package com.kabunx.erp.wrapper;

import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.extension.query.PlusWrapper;
import com.kabunx.erp.model.UserDO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserWrapper extends PlusWrapper<UserDO> {

    public UserWrapper(UserQueryDTO userQueryDTO) {
        super(userQueryDTO);
    }

    /**
     * 该函数可以被指定到query-name的参数查询构造上
     */
    public void whereName(String value) {
        this.eq("name", value);
    }

    public void whereSex(Integer value) {
        this.eq("sex", value);
    }

    public void whereAccount(String account) {
        this.eq("account", account);
    }

    public void eqPhone(String phone) {
        this.eq("phone", phone);
    }
}
