package com.kabunx.erp.service;

import com.kabunx.erp.dependency.UserDependency;
import com.kabunx.erp.domain.AuthRequest;
import com.kabunx.erp.domain.AuthResponse;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.domain.vo.UserVo;
import com.kabunx.erp.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {
    @Resource
    JwtUtil jwt;

    @Resource
    UserDependency userDependency;

    public AuthResponse register(AuthRequest authRequest) {
        // do validation if user already exists
        JsonResponseBody<UserVo> response = userDependency.register(authRequest);
        UserVo userVo = response.getData();

        String accessToken = jwt.generate(userVo, "ACCESS");
        String refreshToken = jwt.generate(userVo, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);

    }
}
