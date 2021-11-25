package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.MemberFeignClient;
import com.kabunx.erp.domain.JsonResponse;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.util.HashUtils;
import com.kabunx.erp.vo.MemberVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    MemberFeignClient memberFeignClient;

    @Override
    public MemberVO findAndValidateMember(String authToken) {
        String[] elements = authToken.split("\\|", 2);
        int userId = Integer.parseInt(elements[0]);
        String plainToken = elements[1];
        JsonResponse<MemberVO> response = memberFeignClient.findByUserId(userId);
        if (response.unavailable()) {
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
