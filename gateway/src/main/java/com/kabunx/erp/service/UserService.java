package com.kabunx.erp.service;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> findById(Long id);

    boolean validateToken(String plainToken, String token);
}
