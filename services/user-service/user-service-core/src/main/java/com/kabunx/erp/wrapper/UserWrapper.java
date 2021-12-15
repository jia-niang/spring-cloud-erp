package com.kabunx.erp.wrapper;

import com.kabunx.erp.domain.dto.UserQueryDTO;
import com.kabunx.erp.extension.wrapper.PlusWrapper;
import com.kabunx.erp.model.UserDO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserWrapper extends PlusWrapper<UserDO> {

    public UserWrapper(UserQueryDTO userQueryDTO) {
        super(userQueryDTO);
    }

    /**
     * 该函数可以被指定到query-name的参数查询构造上
     */
    public void whereName(String value) {
        eq("name", value);
    }

    public void whereSex(Integer value) {
        eq("sex", value);
    }

    public void whereAccount(String account) {
        eq("account", account);
    }

    public void eqPhone(String phone) {
        eq("phone", phone);
    }
}
