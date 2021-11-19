package com.kabunx.erp.service.impl;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.JwtUtils;
import com.kabunx.erp.vo.UserVO;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    UserService userService;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public Optional<User> parseToken(String token) {
        return isCustomToken(token) ? parseCustomToken(token) : parseJwt(token);
    }

    private Optional<User> parseCustomToken(String token) {
        UserVO userVO = userService.findAndValidateByToken(token);
        if (userVO == null) {
            return Optional.empty();
        }
        return Optional.of(User.builder()
                .id(userVO.getId())
                .type("user")
                .build());
    }

    private Optional<User> parseJwt(String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        if (claims.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(User.builder()
                .id((String) claims.get("id"))
                .type((String) claims.get("type"))
                .build());
    }

    /**
     * @param token header token
     * @return 是否为自定义token
     */
    private boolean isCustomToken(String token) {
        return token.contains(SecurityConstant.AUTHORIZATION_CUSTOM_TOKEN_SPLIT);
    }
}
