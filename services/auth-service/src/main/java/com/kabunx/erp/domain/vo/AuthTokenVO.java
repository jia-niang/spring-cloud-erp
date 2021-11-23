package com.kabunx.erp.domain.vo;

import com.kabunx.erp.pojo.BaseVO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenVO extends BaseVO {
    private String accessToken;
    private String refreshToken;
}
