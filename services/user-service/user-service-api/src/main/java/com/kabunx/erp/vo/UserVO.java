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
    private String username;
    private String testKey;
    private String email;
}
