package com.kabunx.erp.service.impl;

import com.kabunx.erp.constant.GlobalConstant;
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

    public Mono<String> findMember(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        String[] elements = token.split(GlobalConstant.BASE_STRING_REGEX, 2);
        if (elements.length != 2) {
            return null;
        }
        long userId = Long.parseLong(elements[0]);
        String plainToken = elements[1];

        return webClient.get()
                .uri("/user/members/{id}", userId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public boolean validateToken(String plainToken, String token) {
        try {
            return token.equals(HashUtils.encryptSha256(plainToken));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
