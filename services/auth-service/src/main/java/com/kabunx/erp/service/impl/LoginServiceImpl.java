package com.kabunx.erp.service.impl;

import com.kabunx.erp.cache.CacheType;
import com.kabunx.erp.domain.dto.LoginCaptchaDTO;
import com.kabunx.erp.domain.dto.LoginMiniAppDTO;
import com.kabunx.erp.domain.dto.LoginSmsCodeDTO;
import com.kabunx.erp.domain.vo.AuthTokenVO;
import com.kabunx.erp.exception.AuthException;
import com.kabunx.erp.service.CaptchaService;
import com.kabunx.erp.service.LoginService;
import com.kabunx.erp.service.UserService;
import com.kabunx.erp.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    CaptchaService captchaService;

    @Resource
    UserService userService;

    @Override
    public AuthTokenVO loginByCaptcha(LoginCaptchaDTO loginCaptchaDTO) {
        // 1、图形验证码是否正确
        boolean isRight = captchaService.verify(
                loginCaptchaDTO.getCaptchaKey(),
                loginCaptchaDTO.getCaptchaCode(),
                CacheType.CAPTCHA
        );
        if (!isRight) {
            throw new AuthException("");
        }
        // 2、服务调用，查找账号
        UserVO user = userService.loadByUsername(loginCaptchaDTO.getAccount());
        // 3、密码校验

        return null;
    }

    @Override
    public AuthTokenVO loginBySmsCode(LoginSmsCodeDTO loginSmsCodeDTO) {
        return null;
    }

    @Override
    public AuthTokenVO loginByMiniApp(LoginMiniAppDTO loginMiniAppDTO) {
        return null;
    }
}
