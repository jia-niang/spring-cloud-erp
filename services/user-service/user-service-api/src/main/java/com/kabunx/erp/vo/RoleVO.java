package com.kabunx.erp.vo;

import com.kabunx.erp.pojo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVO extends BaseVO {
    Long id;

    String name;
}
