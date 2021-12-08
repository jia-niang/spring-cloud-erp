package com.kabunx.erp.service.impl;

import com.kabunx.erp.entity.Member;
import com.kabunx.erp.entity.User;
import com.kabunx.erp.service.AuthenticationService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
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

    public Mono<String> findUserById(Long id) {
        return userService.findById(id);
    }

    @Override
    public Optional<User> validateToken2User(Member member, String token) {
        if (!userService.validateToken(member.getAccessToken(), token)) {
            return Optional.empty();
        }
        return toUserOptional(member);
    }

    public Optional<User> parseJwt2User(String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        if (claims.isEmpty() || isTokenExpired(claims.getExpiration())) {
            return Optional.empty();
        }
        return toUserOptional(claims);
    }

    private boolean isTokenExpired(Date date) {
        return date.before(new Date());
    }

    private Optional<User> toUserOptional(Member member) {
        return Optional.of(User.builder()
                .id(member.getUserId().toString())
                .type("member")
                .build());
    }

    private Optional<User> toUserOptional(Claims claims) {
        return Optional.of(User.builder()
                .id((String) claims.get("id"))
                .type((String) claims.get("type"))
                .build());
    }
}
