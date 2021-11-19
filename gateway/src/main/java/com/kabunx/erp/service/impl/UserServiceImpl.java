package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.HashUtils;
import com.kabunx.erp.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserVO findAndValidateByToken(String authToken) {
        String[] elements = authToken.split("\\|", 2);
        String userId = elements[0];
        String plainToken = elements[1];
        JsonResponseBody<UserVO> response = userFeignClient.show(userId);
        if (!response.isSuccess()) {
            return null;
        }
        String token = response.getData().toString();
        if (validateToken(plainToken, token)) {
            return response.getData();
        }
        return null;
    }

    private boolean validateToken(String plainToken, String token) {
        try {
            return token.equals(HashUtils.encryptSha256(plainToken));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
