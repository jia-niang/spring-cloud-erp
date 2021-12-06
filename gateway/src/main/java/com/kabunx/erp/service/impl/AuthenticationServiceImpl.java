package com.kabunx.erp.service.impl;

import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.entity.Member;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    UserService userService;

    @Resource
    JwtUtils jwtUtils;

    public Mono<String> checkByCustomToken(String token) {
        return userService.findMember(token);
    }

    public Optional<User> parseJwt2User(String token) {
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
