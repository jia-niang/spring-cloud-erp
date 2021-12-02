package com.kabunx.erp.service.impl;

import com.kabunx.erp.api.MemberFeignClient;
import com.kabunx.erp.constant.GlobalConstant;
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
        if (authToken == null || authToken.isEmpty()) {
            return null;
        }
        String[] elements = authToken.split(GlobalConstant.BASE_STRING_REGEX, 2);
        if (elements.length != 2) {
            return null;
        }
        long userId = Long.parseLong(elements[0]);
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
