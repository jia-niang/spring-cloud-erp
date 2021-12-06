package com.kabunx.erp.service;

import reactor.core.publisher.Mono;

public interface UserService {
    public Mono<String> findMember(String token);

    public boolean validateToken(String plainToken, String token);
}
