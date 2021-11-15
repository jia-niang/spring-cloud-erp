package com.kabunx.erp.controller;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.domain.dto.Oauth2TokenDto;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Resource
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/login")
    public Oauth2TokenDto login(
            Principal principal,
            @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        if (accessToken == null) {
            return null;
        }
        return Oauth2TokenDto.builder()
                .token(accessToken.getValue())
                .refreshToken(accessToken.getRefreshToken().getValue())
                .expiresIn(accessToken.getExpiresIn())
                .tokenHeader(SecurityConstant.JWT_TOKEN_PREFIX)
                .build();
    }
}
