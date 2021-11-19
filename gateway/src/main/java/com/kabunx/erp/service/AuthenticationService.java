package com.kabunx.erp.service;

import com.kabunx.erp.entity.User;

import java.util.Optional;

public interface AuthenticationService {

    Optional<User> parseToken(String token);
}
