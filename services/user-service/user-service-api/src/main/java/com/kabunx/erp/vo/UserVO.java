package com.kabunx.erp.vo;

import com.kabunx.erp.pojo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {
    private Long id;
    private String account;
    private String name;
    private Integer sex;
}
