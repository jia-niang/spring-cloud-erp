package com.kabunx.erp.vo;

import com.kabunx.erp.base.Vo;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenVo extends Vo {
    private String id;
}
