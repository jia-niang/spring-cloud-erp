package com.kabunx.erp.vo;

import com.kabunx.erp.pojo.BaseVO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends BaseVO {
    private String id;
    private String account;
    private String name;
    private Integer sex;
}
