package com.kabunx.erp.vo;

import com.kabunx.erp.base.Vo;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo extends Vo {
    private String id;
    private String email;
    private String role;
}
