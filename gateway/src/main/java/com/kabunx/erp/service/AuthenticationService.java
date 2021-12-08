package com.kabunx.erp.service;

import com.kabunx.erp.entity.Member;
import com.kabunx.erp.entity.User;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AuthenticationService {

    Mono<String> findUserById(Long id);

    Optional<User> validateToken2User(Member member, String token);

    Optional<User> parseJwt2User(String token);

}
