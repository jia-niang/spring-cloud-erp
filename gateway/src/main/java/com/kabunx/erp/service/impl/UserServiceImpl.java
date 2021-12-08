package com.kabunx.erp.service.impl;

import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final WebClient webClient;

    public UserServiceImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://erp-user-service").build();
    }

    @Override
    public Mono<String> findById(Long id) {
        return webClient.get()
                .uri("/user/members/{id}", id)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public boolean validateToken(String plainToken, String token) {
        try {
            return token.equals(HashUtils.encryptSha256(plainToken));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
