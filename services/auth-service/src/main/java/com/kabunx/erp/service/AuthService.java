package com.kabunx.erp.service;

import com.kabunx.erp.api.UserFeignClient;
import com.kabunx.erp.constant.AuthConstant;
import com.kabunx.erp.constant.SecurityConstant;
import com.kabunx.erp.domain.dto.RegisterDTO;
import com.kabunx.erp.domain.vo.AuthTokenVO;
import com.kabunx.erp.domain.JsonResponseBody;
import com.kabunx.erp.domain.dto.LoginDTO;
import com.kabunx.erp.dto.UserDTO;
import com.kabunx.erp.vo.UserVO;
import com.kabunx.erp.exception.AuthException;
import com.kabunx.erp.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {
    @Resource
    JwtUtil jwt;

    @Resource
    UserFeignClient userFeignClient;

    public AuthTokenVO login(LoginDTO loginDTO) {
        JsonResponseBody<UserVO> response = userFeignClient.findByAccount(loginDTO.getAccount());
        if (response.hasFallbackError()) {
            throw new AuthException(response.getMessage());
        }
        if (!response.isSuccess()) {
            throw new AuthException(AuthConstant.USERNAME_PASSWORD_ERROR);
        }
        UserVO userVo = response.getData();
        // 检验密码
        String accessToken = jwt.generate(userVo, SecurityConstant.JWT_ACCESS_TYPE);
        String refreshToken = jwt.generate(userVo, SecurityConstant.JWT_REFRESH_TYPE);
        return new AuthTokenVO(accessToken, refreshToken);
    }

    public UserVO register(RegisterDTO registerDTO) {
        // 自己服务的业务逻辑
        // 加密
        UserDTO userDTO = new UserDTO();
        JsonResponseBody<UserVO> response = userFeignClient.create(userDTO);
        if (response.isSuccess()) {
            return response.getData();
        }
        throw new AuthException(response.getMessage());
    }
}
