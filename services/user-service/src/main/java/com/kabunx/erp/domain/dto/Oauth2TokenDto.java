package com.kabunx.erp.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Oauth2TokenDto {
    private String token;
    private String refreshToken;
    private String tokenHeader;
    private int expiresIn;
}
