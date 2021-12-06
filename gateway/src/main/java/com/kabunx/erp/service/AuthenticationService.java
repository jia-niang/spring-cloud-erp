package com.kabunx.erp.service;

import com.kabunx.erp.entity.User;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AuthenticationService {

    Mono<String> checkByCustomToken(String token);

    Optional<User> parseJwt2User(String token);
}
