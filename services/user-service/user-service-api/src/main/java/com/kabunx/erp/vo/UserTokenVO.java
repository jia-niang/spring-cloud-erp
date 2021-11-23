package com.kabunx.erp.vo;

import com.kabunx.erp.pojo.BaseVO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenVO extends BaseVO {
    private String id;
}
