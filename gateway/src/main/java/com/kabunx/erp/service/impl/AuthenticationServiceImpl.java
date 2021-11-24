package com.kabunx.erp.service.impl;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.JwtUtils;
import com.kabunx.erp.vo.MemberVO;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    /**
     * @param token header token
     * @return 是否为自定义token
     */
    private boolean isCustomToken(String token) {
        return token.contains(SecurityConstant.AUTHORIZATION_CUSTOM_TOKEN_SPLIT);
    }

    private Optional<User> parseCustomToken(String token) {
        MemberVO member = userService.findAndValidateMember(token);
        if (member == null) {
            return Optional.empty();
        }
        return Optional.of(User.builder()
                .id(member.getUserId().toString())
                .type("member")
                .build());
    }

    private Optional<User> parseJwt(String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        if (claims.isEmpty() || isTokenExpired(claims.getExpiration())) {
            return Optional.empty();
        }
        return Optional.of(User.builder()
                .id((String) claims.get("id"))
                .type((String) claims.get("type"))
                .build());
    }

    private boolean isTokenExpired(Date date) {
        return date.before(new Date());
    }
}
